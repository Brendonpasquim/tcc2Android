package br.com.brendon.tcc2;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Toast;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.util.Utils;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.overlay.Marker;

import org.mapsforge.map.rendertheme.InternalRenderTheme;
import org.mapsforge.map.rendertheme.XmlRenderTheme;
import org.mapsforge.samples.android.SamplesBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ExemploPlotPontosOnibus extends SamplesBaseActivity {

    @Override
    protected XmlRenderTheme getRenderTheme() {
        return InternalRenderTheme.DEFAULT;
    }

    protected LatLong pontoOnibus1 = new LatLong(52.5, 13.4);

    protected void addOverlayLayers(Layers layers){
        List<LatLong> pontosOnibus = new ArrayList<>();
        pontosOnibus.add(pontoOnibus1);
    }

    private void adicionarPontosDeOnibusNoMapa(List<LatLong> latLongsPontosOnibus, Layers layers){

        if(latLongsPontosOnibus == null || latLongsPontosOnibus.isEmpty()){
            return;
        }

        for(LatLong latLongPonto : latLongsPontosOnibus){
            Marker marker = createTappableMarker(this, R.drawable.marker_silver_green, latLongPonto);
            layers.add(marker);
        }
    }

    private Marker createTappableMarker(final Context c, int resourceIdentifier, LatLong latLong) {
        Drawable drawable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? c.getDrawable(resourceIdentifier) : c.getResources().getDrawable(resourceIdentifier);
        Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
        bitmap.incrementRefCount();
        return new Marker(latLong, bitmap, 0, -bitmap.getHeight() / 2) {
            @Override
            public boolean onTap(LatLong geoPoint, Point viewPosition, Point tapPoint) {
                if (contains(viewPosition, tapPoint)) {
                    Toast.makeText(c,"The Marker was tapped " + geoPoint.toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        };
    }
}
