package pl.droidsonroids.gif;

import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import pl.droidsonroids.gif.GifTextureView;

/* loaded from: classes.dex */
class PlaceholderDrawingSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    private final GifTextureView.PlaceholderDrawListener mDrawer;

    PlaceholderDrawingSurfaceTextureListener(GifTextureView.PlaceholderDrawListener drawer) {
        this.mDrawer = drawer;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) throws Surface.OutOfResourcesException, IllegalArgumentException {
        Surface surface = new Surface(surfaceTexture);
        Canvas canvas = surface.lockCanvas(null);
        this.mDrawer.onDrawPlaceholder(canvas);
        surface.unlockCanvasAndPost(canvas);
        surface.release();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}