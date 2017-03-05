/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiClient;

import TokenizerUtils.VietTokenizerSingleton;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Admin
 */
public class ServerFrm extends javax.swing.JFrame {

    /**
     * Creates new form ServerFrm
     */
    private VietTokenizerSingleton vietTokenizer;
    private final int PORT = 1993;
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ClientService clientService;

    public ServerFrm() {
        super("Server tokenizer");
        try {
            initComponents();
            vietTokenizer = VietTokenizerSingleton.getInstance();
            clientService = new ClientService();
            clientService.connectServer();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    class multiClient implements Runnable {

        private Socket clientSock = null;
        private JTextArea room = null;

        public multiClient(Socket clientSock, JTextArea room) {
            this.clientSock = clientSock;
            this.room = room;
        }

        @Override
        public void run() {
            DataInputStream dis = null;
            DataOutputStream dos = null;
            String msg = null;
            try {
                dis = new DataInputStream(clientSock.getInputStream());
                dos = new DataOutputStream(clientSock.getOutputStream());
                while (true) {
                    msg = dis.readUTF();
                    if (msg != null && !msg.isEmpty()) {
                        multiRequest mRequest = new multiRequest(dos, msg);
                        Thread requet = new Thread(mRequest);
                        requet.run();
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    class multiRequest implements Runnable {

        private DataOutputStream dos = null;
        private String msg = null;

        public multiRequest(DataOutputStream dos, String msg) {
            this.dos = dos;
            this.msg = msg;
        }

        @Override
        public void run() {
            try {

                msg = vietTokenizer.processTokenizer(msg);
                RoomMain.append("Client: " + msg + "\n");
                msg = requestTensorflow(msg);
                dos.writeUTF(msg);
                RoomMain.append("Client-token: " + msg + "\n");
//                System.out.println("MSG-res:" + msg);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public String requestTensorflow(String s) {
        String result = "";
        try {
            result = clientService.requestFromServer(s);
            result=result.replace("_", " ");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public void ListenClient() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(PORT);
                    while (true) {
                        multiClient mc;
                        mc = new multiClient(serverSocket.accept(), RoomMain);
                        System.out.println("connect ");
                        Thread tr = new Thread(mc);
                        tr.start();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        thread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        RoomMain = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RoomMain.setColumns(20);
        RoomMain.setRows(5);
        jScrollPane1.setViewportView(RoomMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ServerFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ServerFrm sfrm = new ServerFrm();
                sfrm.setVisible(true);
                sfrm.ListenClient();

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea RoomMain;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
