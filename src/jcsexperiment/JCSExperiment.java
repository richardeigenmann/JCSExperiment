package jcsexperiment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;

/**
 *
 * @author Richard Eigenmann
 */
public class JCSExperiment extends javax.swing.JFrame {

    /**
     * update frequency for the cache stats
     */
    private static final int REFRESH_INTERVAL = 400; //ms

    /**
     * period in miliseconds between gets on the randomWalker timer
     */
    private static final int CACHE_RETRIEVAL_INTERVAL = 400; //ms

    private static final String cacheRegionName = "testCache";

    private CacheAccess<String, CacheableObject> cacheAccess;

    /**
     * Creates new form CachePerformanceTester
     */
    public JCSExperiment() {
        initComponents();

        try {
            cacheAccess = JCS.getInstance( cacheRegionName );
        } catch ( CacheException ex ) {
            LOGGER.severe( ex.getLocalizedMessage() );
        }

        Timer updateTimer = new Timer( REFRESH_INTERVAL, new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                updateInfo();
            }
        } );
        updateTimer.start();
    }

    /**
     * Get timer which calls a random cache get
     */
    private Timer randomWalker;

    /**
     * starts the cache get timer
     */
    private void startGet() {

        randomWalker = new Timer( CACHE_RETRIEVAL_INTERVAL, new ActionListener() {

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

    /**
     * ends the cache get timer
     */
    private void stopGet() {
        randomWalker.stop();
    }

    private void loadObjects() {
        int number = numberField.getValue();
        for ( int i = 0; i < number; i++ ) {
            String key = String.format( "Key:%d", i + objectsCachedCount );

            CacheableObject cacheableObject = new CacheableObject( new byte[(int) sizeField.getValue()] );
            try {
                cacheAccess.put( key, cacheableObject );
                progressTextArea.append( String.format ("Put object: %s\n", key ) );
            } catch ( CacheException ex ) {
                LOGGER.info( ex.getLocalizedMessage() );
            }
        }

        progressTextArea.append( String.format( "Added %d Objects.\n", number ) );
        objectsCachedCount += number;
        updateInfo();

    }

    private void hitCache() throws MalformedURLException {
        if ( objectsCachedCount > 0 ) {
            int randomKey = (int) ( Math.random() * objectsCachedCount );
            String key = String.format( "Key:%d", randomKey );
            CacheableObject cacheableObject = (CacheableObject) cacheAccess.get( key );
            progressTextArea.append( "Retrieved " );
            progressTextArea.append( key );
            if ( cacheableObject == null ) {
                // cache miss
                progressTextArea.append( " as null\n" );

                if ( cacheMissPutCheckbox.isSelected() ) {
                    //does it make a difference if we now try to put a missed object?
                    cacheableObject = new CacheableObject( new byte[(int) sizeField.getValue()] );
                    progressTextArea.append( "   --> doing put after cache miss\n" );
                    try {
                        cacheAccess.put( key, cacheableObject );
                    } catch ( CacheException ex ) {
                        LOGGER.info( ex.getLocalizedMessage() );
                    }
                }
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

        cacheInfo.setText( cacheAccess.getStats() );
        objectsCached.setText( Integer.toString( objectsCachedCount ) );
    }

    /**
     * Defines a logger for this class
     */
    private static final Logger LOGGER = Logger.getLogger( JCSExperiment.class.getName() );

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
        startGetButton = new javax.swing.JButton();
        stopGetButton = new javax.swing.JButton();
        cacheMissPutCheckbox = new javax.swing.JCheckBox();

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

        jLabel9.setText("Objects Cached:");

        objectsCached.setText("jLabel9");

        jLabel3.setText("Size (in KB):");

        jLabel4.setText("Number to load:");

        startGetButton.setText("Start get");
        startGetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGetButtonActionPerformed(evt);
            }
        });

        stopGetButton.setText("Stop get");
        stopGetButton.setEnabled(false);
        stopGetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopGetButtonActionPerformed(evt);
            }
        });

        cacheMissPutCheckbox.setText("Do a put after a cache miss?");
        cacheMissPutCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cacheMissPutCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cacheMissPutCheckbox)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(totalMemoryLabel)
                            .addComponent(jLabel8))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(freeMemory)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(maxMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(totalMemory))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sizeField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numberField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(objectsCached)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(startGetButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stopGetButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(103, 103, 103))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(freeMemory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(objectsCached)
                            .addComponent(jLabel9)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cacheMissPutCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(startGetButton)
                            .addComponent(stopGetButton))))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadObjects();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void startGetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGetButtonActionPerformed
        startGet();
        startGetButton.setEnabled( false );
        stopGetButton.setEnabled( true );
    }//GEN-LAST:event_startGetButtonActionPerformed

    private void stopGetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopGetButtonActionPerformed
        stopGet();
        startGetButton.setEnabled( true );
        stopGetButton.setEnabled( false );
    }//GEN-LAST:event_stopGetButtonActionPerformed

    private void cacheMissPutCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cacheMissPutCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cacheMissPutCheckboxActionPerformed

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
        } catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex ) {
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
    private javax.swing.JCheckBox cacheMissPutCheckbox;
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
    private javax.swing.JButton startGetButton;
    private javax.swing.JButton stopGetButton;
    private javax.swing.JLabel totalMemory;
    private javax.swing.JLabel totalMemoryLabel;
    // End of variables declaration//GEN-END:variables

}
