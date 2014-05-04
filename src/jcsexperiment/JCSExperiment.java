package jcsexperiment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.apache.commons.jcs.access.exception.CacheException;

/**
 *
 * @author Richard Eigenmann
 */
public class JCSExperiment extends javax.swing.JFrame {

    private static final int REFRESH_INTERVAL = 400; //ms
    private static final int CACHE_RETRIEVAL_INTERVAL = 400; //ms

    /**
     * Creates new form CachePerformanceTester
     */
    public JCSExperiment() {
        initComponents();

        timer = new Timer( REFRESH_INTERVAL, new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                updateInfo();
            }
        } );
        timer.start();

        Timer randomWalker = new Timer( CACHE_RETRIEVAL_INTERVAL, new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    hitCache();
                } catch ( MalformedURLException ex ) {
                    Logger.getLogger( JCSExperiment.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
        } );
        randomWalker.start();
    }

    private final Timer timer;

    private void loadObjects() {
        int number = numberField.getValue();
        for ( int i = 0; i < number; i++ ) {
            String key = String.format( "Key:%d", i );

            CacheableObject cacheableObject = new CacheableObject( new byte[(int) sizeField.getValue()] );
            try {
                JcsCache.getInstance().getCacheAccessForTesting().put( key, cacheableObject );
            } catch ( CacheException ex ) {
                LOGGER.info( ex.getLocalizedMessage() );
            }
        }

        progressTextArea.append( String.format( "Added %d Pictures.\n", number ) );
        objectsCachedCount += number;
        updateInfo();

    }

    private void hitCache() throws MalformedURLException {
        if ( objectsCachedCount > 0 ) {
            int randomKey = (int) ( Math.random() * objectsCachedCount );
            String key = String.format( "Key:%d", randomKey );
            CacheableObject cacheableObject = (CacheableObject) JcsCache.getInstance().getCacheAccessForTesting().get( key );
            progressTextArea.append( "Retrieved " );
            progressTextArea.append( key.toString() );
            if ( cacheableObject == null ) {
                progressTextArea.append( " as null\n" );
            } else {
                progressTextArea.append( "\n" );
            }
        }
    }

    /**
     * Remember the number of pictures cached
     */
    private int objectsCachedCount;

    private void updateInfo() {
        int freeMemoryMB = (int) ( Runtime.getRuntime().freeMemory() / 1024 / 1024 );
        int totalMemoryMB = (int) ( Runtime.getRuntime().totalMemory() / 1024 / 1024 );
        int maxMemoryMB = (int) ( Runtime.getRuntime().maxMemory() / 1024 / 1024 );

        freeMemory.setText( String.format( "%dMB", freeMemoryMB ) );
        totalMemory.setText( String.format( "%dMB", totalMemoryMB ) );
        maxMemory.setText( String.format( "%dMB", maxMemoryMB ) );

        cacheInfo.setText( JcsCache.getInstance().getHighresCacheStats() );
        objectsCached.setText( Integer.toString( objectsCachedCount ) );
    }

    /**
     * Defines a logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger( JcsCache.class.getName() );

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        maxMemory = new javax.swing.JLabel();
        totalMemoryLabel = new javax.swing.JLabel();
        totalMemory = new javax.swing.JLabel();
        freeMemory = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        progressTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cacheInfo = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        objectsCached = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        sizeField = new jcsexperiment.WholeNumberField(10,5);
        numberField = new jcsexperiment.WholeNumberField(25,5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setText("JCS Tester");

        jLabel2.setText("JVM Max Memory:");

        maxMemory.setText("jLabel3");

        totalMemoryLabel.setText("Total Memory:");

        totalMemory.setText("jLabel5");

        freeMemory.setText("jLabel9");

        progressTextArea.setColumns(20);
        progressTextArea.setRows(5);
        jScrollPane1.setViewportView(progressTextArea);

        jButton1.setText("Load objects");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Progress");

        jLabel7.setText("Cache Info");

        cacheInfo.setColumns(20);
        cacheInfo.setRows(5);
        jScrollPane2.setViewportView(cacheInfo);

        jLabel8.setText("Free Memory");

        jLabel9.setText("Pictures Cached:");

        objectsCached.setText("jLabel9");

        jLabel3.setText("Size (in KB):");

        jLabel4.setText("Number to load:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(110, 110, 110))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(totalMemoryLabel)
                            .addComponent(jLabel8))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(freeMemory)
                                    .addComponent(objectsCached))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(maxMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(totalMemory))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sizeField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel9))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maxMemory)
                    .addComponent(jLabel3)
                    .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalMemoryLabel)
                    .addComponent(totalMemory)
                    .addComponent(jLabel4)
                    .addComponent(numberField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(freeMemory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(objectsCached)
                            .addComponent(jLabel9)))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadObjects();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] ) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException ex ) {
            java.util.logging.Logger.getLogger( JCSExperiment.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( InstantiationException ex ) {
            java.util.logging.Logger.getLogger( JCSExperiment.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( IllegalAccessException ex ) {
            java.util.logging.Logger.getLogger( JCSExperiment.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( JCSExperiment.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( new Runnable() {
            @Override
            public void run() {
                new JCSExperiment().setVisible( true );
            }
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea cacheInfo;
    private javax.swing.JLabel freeMemory;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel maxMemory;
    private jcsexperiment.WholeNumberField numberField;
    private javax.swing.JLabel objectsCached;
    private javax.swing.JTextArea progressTextArea;
    private jcsexperiment.WholeNumberField sizeField;
    private javax.swing.JLabel totalMemory;
    private javax.swing.JLabel totalMemoryLabel;
    // End of variables declaration//GEN-END:variables

}
