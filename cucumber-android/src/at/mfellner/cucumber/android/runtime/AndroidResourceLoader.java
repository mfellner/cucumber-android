package at.mfellner.cucumber.android.runtime;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import at.mfellner.cucumber.android.api.CucumberInstrumentation;
import cucumber.runtime.io.Resource;
import cucumber.runtime.io.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AndroidResourceLoader implements ResourceLoader {
    private Context mContext;

    public AndroidResourceLoader(Context context) {
        mContext = context;
    }

    @Override
    public Iterable<Resource> resources(String path, String suffix) {
        List<Resource> resources = new ArrayList<Resource>();
        AssetManager am = mContext.getAssets();
        try {
            for (String file_name : am.list(path)) {
                if (file_name.endsWith(suffix)) {
                    Resource as = new AndroidResource(mContext, String.format("%s/%s", path, file_name));
                    resources.add(as);
                }
            }
        } catch (IOException e) {
            Log.e(CucumberInstrumentation.TAG, "AndroidResourceLoader.resources " + e.toString());
        }
        return resources;
    }
}
