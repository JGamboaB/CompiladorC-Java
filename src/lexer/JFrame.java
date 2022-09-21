package lexer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class JFrame extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    public JFrame() {
        initComponents();
        populateMainTable();
        populateErrorsTable();
    }
    
    public void populateMainTable(){
        String[] columns = {"Token", "Type", "Amount", "Line(s)"};
        String[][] data = {
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"},
            {"Test","TEST","2","1,2"},{"Test2","TEST","3","1,2(2)"}
        };
        DefaultTableModel model = new DefaultTableModel(data,columns){

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        tableMain.setModel(model);
        tableMain.setBackground(Color.lightGray);
    };
    
    public void populateErrorsTable(){
        String[] columns = {"Token", "Line(s)"};
        String[][] data = {
            {"Test","2"},{"Test2","4"}
        };
        DefaultTableModel model = new DefaultTableModel(data,columns){

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        
        tableErrors.setModel(model);
        tableErrors.setBackground(Color.lightGray);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMain = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableErrors = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("C Lexical Scanner");

        jButton.setText("Select File");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        tableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableMain);

        tableErrors.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableErrors);

        jLabel2.setText("Errors");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        
        
        try {
            Reader reader = new BufferedReader(new FileReader(filename));
            Lexer lexer = new Lexer(reader);
            String result = "";
            String errors = "";
            TokenCounter tokenCounter = new TokenCounter();
            while(true){                
                try {
                    Token token = lexer.yylex();
                    if (token == null){
                        result += "END";
                        System.out.println(result);
                        System.out.println(errors);
                        System.out.println(tokenCounter.toString());
                        return;
                    }
                    tokenCounter.countToken(token);
                    switch (token.kindToken){
                        case RESERVED_WORD:
                            result += lexer.lexeme + ": RESERVED_WORD\t Line: "+token.line+"\n"; break;
                        case IDENTIFIER:
                            result += lexer.lexeme + ": IDENTIFIER\t Line: "+token.line+"\n"; break;
                        case LITERAL:
                            result += lexer.lexeme + ": LITERAL\t Line: "+token.line+"\n"; break;
                        case OPERATOR:
                            result += lexer.lexeme + ": OPERATOR\t Line: "+token.line+"\n"; break;
                        default:
                            result += "Token: " + token + "\n";
                            break;
                    }
                } catch(LexicalError ex) {
                    errors += ex.getMessage() +"\t Line: "+ex.getLine()+"\n";
                }
            }
           
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
         
        
        
        System.out.println(filename);
    }//GEN-LAST:event_jButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableErrors;
    private javax.swing.JTable tableMain;
    // End of variables declaration//GEN-END:variables
}
