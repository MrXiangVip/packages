package com.example.launcher;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;

import com.example.launcher.util.DefaultDisplay;
import com.example.launcher.util.MainThreadInitializedObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class InvariantDeviceProfile {
    public int numRows;
    public int numColumns;
    public int numAllAppsColumns;
    public DeviceProfile landscapeProfile;
    public DeviceProfile portraitProfile;
    public static final MainThreadInitializedObject<InvariantDeviceProfile> INSTANCE =
            new MainThreadInitializedObject<>(InvariantDeviceProfile::new);
    private static final String KEY_IDP_GRID_NAME = "idp_grid_name";
    private final ArrayList<OnIDPChangeListener> mChangeListeners = new ArrayList<>();

    public InvariantDeviceProfile() {
    }

    private InvariantDeviceProfile(InvariantDeviceProfile p) {
        numRows = p.numRows;
        numColumns = p.numColumns;
        numAllAppsColumns = p.numAllAppsColumns;

    }

    private InvariantDeviceProfile(Context context) {
        String gridName = getCurrentGridName(context);

        String newGridName = initGrid(context, gridName);

    }
    public void addOnChangeListener(OnIDPChangeListener listener) {
        mChangeListeners.add(listener);
    }

    public DeviceProfile getDeviceProfile(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE ? landscapeProfile : portraitProfile;
    }

    public static String getCurrentGridName(Context context) {
        return Utilities.isGridOptionsEnabled(context)
                ? Utilities.getPrefs(context).getString(KEY_IDP_GRID_NAME, null) : null;
    }

    private String initGrid(Context context, String gridName) {
        DefaultDisplay.Info displayInfo = DefaultDisplay.INSTANCE.get(context).getInfo();
        ArrayList<DisplayOption> allOptions = getPredefinedDeviceProfiles(context, gridName);

        DisplayOption displayOption = invDistWeightedInterpolate(displayInfo, allOptions);
        initGrid(context, displayInfo, displayOption);
        return displayOption.grid.name;
    }
    static DisplayOption invDistWeightedInterpolate(
            DefaultDisplay.Info displayInfo, ArrayList<DisplayOption> points) {
        GridOption closestOption = points.get(0).grid;
        float weights = 0;

        DisplayOption out = new DisplayOption(closestOption);
        return out.multiply(1.0f / weights);


    }
    private void initGrid(
            Context context, DefaultDisplay.Info displayInfo, DisplayOption displayOption) {
        GridOption closestProfile = displayOption.grid;
        numAllAppsColumns = closestProfile.numAllAppsColumns;

        Point realSize = new Point(displayInfo.realSize);

        int smallSide = Math.min(realSize.x, realSize.y);
        int largeSide = Math.max(realSize.x, realSize.y);

        DeviceProfile.Builder builder = new DeviceProfile.Builder(context, this, displayInfo)
                .setSizeRange(new Point(displayInfo.smallestSize),
                        new Point(displayInfo.largestSize));
        landscapeProfile = builder.setSize(largeSide, smallSide).build();
        portraitProfile = builder.setSize(smallSide, largeSide).build();
    }
    static ArrayList<DisplayOption> getPredefinedDeviceProfiles(Context context, String gridName) {
        ArrayList<DisplayOption> profiles = new ArrayList<>();
        try (XmlResourceParser parser = context.getResources().getXml(R.xml.device_profiles)) {
            final int depth = parser.getDepth();
            int type;
            while (((type = parser.next()) != XmlPullParser.END_TAG ||
                    parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
                if ((type == XmlPullParser.START_TAG)
                        && GridOption.TAG_NAME.equals(parser.getName())) {

                    GridOption gridOption = new GridOption(context, Xml.asAttributeSet(parser));
                    final int displayDepth = parser.getDepth();
                    while (((type = parser.next()) != XmlPullParser.END_TAG ||
                            parser.getDepth() > displayDepth)
                            && type != XmlPullParser.END_DOCUMENT) {
                        if ((type == XmlPullParser.START_TAG) && "display-option".equals(
                                parser.getName())) {
                            profiles.add(new DisplayOption(
                                    gridOption, context, Xml.asAttributeSet(parser)));
                        }
                    }
                }
            }
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }

        ArrayList<DisplayOption> filteredProfiles = new ArrayList<>();
        if (!TextUtils.isEmpty(gridName)) {
            for (DisplayOption option : profiles) {
                if (gridName.equals(option.grid.name)) {
                    filteredProfiles.add(option);
                }
            }
        }
        if (filteredProfiles.isEmpty()) {
            // No grid found, use the default options
            for (DisplayOption option : profiles) {
                if (option.canBeDefault) {
                    filteredProfiles.add(option);
                }
            }
        }
        if (filteredProfiles.isEmpty()) {
            throw new RuntimeException("No display option with canBeDefault=true");
        }
        return filteredProfiles;

    }
    private static final class DisplayOption {
        private final GridOption grid;
        private final float minWidthDps;
        private final float minHeightDps;
        private final boolean canBeDefault;

        private float iconSize;
        private float iconTextSize;
        private float landscapeIconSize;
        private float allAppsIconSize;
        private float allAppsIconTextSize;

        DisplayOption(GridOption grid, Context context, AttributeSet attrs) {
            this.grid = grid;
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.ProfileDisplayOption);
            minWidthDps = a.getFloat(R.styleable.ProfileDisplayOption_minWidthDps, 0);
            minHeightDps = a.getFloat(R.styleable.ProfileDisplayOption_minHeightDps, 0);
            canBeDefault = a.getBoolean(
                    R.styleable.ProfileDisplayOption_canBeDefault, false);

            iconSize = a.getFloat(R.styleable.ProfileDisplayOption_iconImageSize, 0);
            landscapeIconSize = a.getFloat(R.styleable.ProfileDisplayOption_landscapeIconSize,
                    iconSize);
            iconTextSize = a.getFloat(R.styleable.ProfileDisplayOption_iconTextSize, 0);

            allAppsIconSize = a.getFloat(R.styleable.ProfileDisplayOption_allAppsIconSize,
                    iconSize);
            allAppsIconTextSize = a.getFloat(R.styleable.ProfileDisplayOption_allAppsIconTextSize,
                    iconTextSize);
            a.recycle();

        }

        DisplayOption(GridOption grid) {
            this.grid = grid;
            minWidthDps = 0;
            minHeightDps = 0;
            canBeDefault = false;
        }
        private DisplayOption multiply(float w) {
            iconSize *= w;
            landscapeIconSize *= w;
            allAppsIconSize *= w;
            iconTextSize *= w;
            allAppsIconTextSize *= w;
            return this;
        }
    }
    public interface OnIDPChangeListener {

        void onIdpChanged(int changeFlags, InvariantDeviceProfile profile);
    }

    public static final class GridOption {
        public static final String TAG_NAME = "grid-option";

        public final String name;
        public final int numRows;
        public final int numColumns;
        private final int numAllAppsColumns;

        public GridOption(Context context, AttributeSet attrs) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.GridDisplayOption);
            name = a.getString(R.styleable.GridDisplayOption_name);
            numRows = a.getInt(R.styleable.GridDisplayOption_numRows, 0);
            numColumns = a.getInt(R.styleable.GridDisplayOption_numColumns, 0);

            numAllAppsColumns = a.getInt(
                    R.styleable.GridDisplayOption_numAllAppsColumns, numColumns);
        }
    }
}