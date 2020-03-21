package cafe.adriel.androidaudioconverter;

import android.content.Context;
import android.util.Log;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;

public class AndroidAudioConverter {
    private static boolean loaded;

    private Context context;
    private File audioFile;
    private AudioFormat format;
    private IConvertCallback callback;
    static String LOG_TAG = "AndroidAudioConverter";

    private AndroidAudioConverter(Context context){
        this.context = context;
    }

    public static boolean isLoaded(){
        return loaded;
    }

    public static void load(Context context, final ILoadCallback callback){
        try {
            List<String> externalLibraries = Config.getExternalLibraries();
            Log.i(LOG_TAG, String.valueOf(externalLibraries));

            loaded = true;
            callback.onSuccess();
        } catch (Exception e){
            loaded = false;
            callback.onFailure(e);
        }
    }

    public static AndroidAudioConverter with(Context context) {
        return new AndroidAudioConverter(context);
    }

    public AndroidAudioConverter setFile(File originalFile) {
        this.audioFile = originalFile;
        return this;
    }

    public AndroidAudioConverter setFormat(AudioFormat format) {
        this.format = format;
        return this;
    }

    public AndroidAudioConverter setCallback(IConvertCallback callback) {
        this.callback = callback;
        return this;
    }

    public void convert() {
        if (!isLoaded()){
            callback.onFailure(new Exception("FFmpeg not loaded"));
            return;
        }

        if (audioFile == null || !audioFile.exists()){
            callback.onFailure(new IOException("File not exists"));
            return;
        }
        if (!audioFile.canRead()){
            callback.onFailure(new IOException("Can't read the file. Missing permission?"));
            return;
        }
        final File convertedFile = getConvertedFile(audioFile, format);
        final String[] cmd = new String[]{"-y", "-i", audioFile.getPath(), convertedFile.getPath()};
        try {
            int rc = FFmpeg.execute(cmd);
            if (rc == RETURN_CODE_SUCCESS) {
                Log.i(Config.TAG, "Command execution completed successfully.");
                callback.onSuccess(convertedFile);
            } else if (rc == RETURN_CODE_CANCEL) {
                Log.i(Config.TAG, "Command execution cancelled by user.");
                callback.onFailure(new IOException("Command execution cancelled by user."));
            } else {
                Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
                callback.onFailure(new IOException(String.format("Command execution failed with rc=%d and the output below.", rc)));
                Config.printLastCommandOutput(Log.INFO);
            }
        } catch (Exception e){
            callback.onFailure(e);
        }
    }

    private static File getConvertedFile(File originalFile, AudioFormat format){
        String[] f = originalFile.getPath().split("\\.");
        String filePath = originalFile.getPath().replace(f[f.length - 1], format.getFormat());
        return new File(filePath);
    }
}
