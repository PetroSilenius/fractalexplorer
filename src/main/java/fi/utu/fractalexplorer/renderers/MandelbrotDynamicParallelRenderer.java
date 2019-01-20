package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * You need to implement this!
 */
public interface MandelbrotDynamicParallelRenderer extends MandelbrotRenderer {
    default void drawSet(Viewport vp) {

        //Luodaan "allas" threadeja, määrä 4
        ExecutorService executor = Executors.newFixedThreadPool(4);

        /**
         * Käydään läpi jokainen kuvan ruutu, ruutuja yhteensä 64
         * Alustetaan threadit ja jokainen thread suorittaa ruudun kerrallaan
         * Kun thread on valmis, siirtyy se seuraavan aloittamattoman ruudun suorittamiseen
         */

        for (int y = 0; y <= 7; y += 1) {
            int ty = y * (renderHeight() / 8);
            for (int x = 0; x <= 7; x += 1) {
                int tx = x * (renderWidth() / 8);
                Runnable worker = new Runnable() {
                    @Override
                    public void run() {
                            drawTile(tx, ty, renderWidth() / 8, renderHeight() / 8, vp);
                    }
                };
                executor.execute(worker);
            }
            //Piirtää puuttuvan osuuden, joka tapahtuu kun renderWidth ei ole jaollinen kahdeksalla
            drawTile(8 * (renderWidth()/8), ty, renderWidth()-8 * (renderWidth()/8), renderHeight()/8, vp);
        }

        //Piirtää puuttuvan osuuden, joka tapahtuu kun renderHeight ei ole jaollinen kahdeksalla
        for (int x = 0; x <= 7; x += 1) {
            int tx = x * (renderWidth() / 8);
            Runnable worker = new Runnable() {
                @Override
                public void run() {
                    drawTile(tx, 8 * (renderHeight()/8), renderWidth() / 8, renderHeight()-8 * (renderHeight()/8), vp);
                }
            };
            executor.execute(worker);
        }
        //Piirtää puuttuvan osuuden, joka tapahtuu kun renderHeight ja renderWidth eivät ole jaollisia kahdeksalla
        drawTile(8 * (renderWidth()/8), 8 * (renderHeight()/8), renderWidth()-8 * (renderWidth()/8), renderHeight() - 8 * (renderHeight()/8), vp);


        //Varmistetaan, ettei executor ota uusia tehtäviä suoritettaviksi
        //ja odotetaan että suoritettavana olevat tehtävät viedään loppuun
        executor.shutdown();
        try {
            executor.awaitTermination(6000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}