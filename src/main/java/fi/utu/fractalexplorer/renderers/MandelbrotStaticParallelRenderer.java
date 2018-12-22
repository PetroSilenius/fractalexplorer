package fi.utu.fractalexplorer.renderers;

import fi.utu.fractalexplorer.util.Viewport;

/**
 * You need to implement this!
 */
public interface MandelbrotStaticParallelRenderer extends MandelbrotRenderer {
    default void drawSet(Viewport vp) {
        //Luodaan taulukko threadeja, määrä 4
        Thread[] threads = new Thread[4];

        /**
         * Suoritetaan jokaiselle threadille
         * Piirretään drawTilellä jokaisella threadilla oma alueensa
         * Kuva jaettu kahdeksaan vaakasuoraan alueeseen, 2 per threadi
         * Jokainen threadi piirtää oman indeksinsä mukaisen alueen sekä indeksi + 4
         */
        for (int i=0; i<threads.length; i++){
            int ty = i * renderHeight()/8;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        drawTile(0, ty, renderWidth(), renderHeight() / 8, vp);
                        drawTile(0, ty + renderHeight()/2, renderWidth(), renderHeight() / 8, vp);
                    }
                }
            });
        }

        //Käynnistetään threadeilla piirto
        try{
            for(int j= 0; j<threads.length; j++){
                threads[j].start();
                threads[j].join();
            }
        }catch(InterruptedException e){
            System.out.println(e.toString());
        }
    }
}