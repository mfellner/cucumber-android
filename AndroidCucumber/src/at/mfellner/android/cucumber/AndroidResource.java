// Copyright © 2013 Maximilian Fellner <max.fellner@gmail.com>

package at.mfellner.android.cucumber;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import cucumber.runtime.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class AndroidResource implements Resource {
    private final Context mContext;
    private final String mPath;

    public AndroidResource(Context context, String path) {
        Log.d("CUCUMBER", String.format("New AndroidResource '%s'", path));
        mContext = context;
        mPath = path;
    }

    @Override
    public String getPath() {
        return mPath;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return mContext.getAssets().open(mPath, AssetManager.ACCESS_UNKNOWN);
    }

    @Override
    public String getClassName() {
        return mPath.substring(mPath.lastIndexOf("/"));
    }
}
