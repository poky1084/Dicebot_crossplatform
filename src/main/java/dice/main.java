/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package dice;


import org.luaj.vm2.*;

import javax.swing.table.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.Double.parseDouble;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.io.*;
import org.fife.util.*;
import org.fife.print.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import org.json.*;
import org.jfree.chart.ChartPanel;
//import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 *
 * @author Zsolt
 */



public class main extends javax.swing.JFrame {

    String sitemirror = "stake.com";
    String apikey = "";
    boolean running = false;    
    int bets = 0;
    double betamount = 0.00001;
    Boolean win = false;

    double profit = 0.00000000;
    String condition = "above";
    double nexttarget = 2;
    double var_balance = 0;
    Integer var_currentstreak = 0;
    double var_previousbet = 0;
    double var_chance = 98;
    Boolean var_bethigh = true;
    Integer var_wins = 0;
    Integer var_losses = 0;
    String var_currency = "doge";
    double var_wagered = 0;
    double var_currentprofit = 0;
    Integer winstreak = 0;
    Integer losestreak = 0;
    int rowCountTable = 10;
    List<Integer> maxwinstreak = new ArrayList<Integer>();
    List<Integer> maxlosestreak = new ArrayList<Integer>();
    List<Double> maxhighestbet = new ArrayList<Double>();

    Integer startDate = (int) System.currentTimeMillis();
    Integer start_epos = (int) System.currentTimeMillis();
    Integer final_epos = (int) System.currentTimeMillis();   

    XYSeries series = new XYSeries("dataset");
    XYSeries series2 = new XYSeries("");
    DecimalFormat format8 = new DecimalFormat("0.00000000");
    DecimalFormat format4 = new DecimalFormat("0.0000");
    DecimalFormat format2 = new DecimalFormat("0.00");  
    DecimalFormat format1 = new DecimalFormat("0.0");

    Globals global_var = JsePlatform.standardGlobals(); 
    //ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //PrintStream printStream = new PrintStream(baos);
    CustomRenderer colouringTable = new CustomRenderer();
    public static class lastBet {
        public static double Roll = 0;
 
    }
    
    //public void PrintToStringFromLuaj() {

        
        //String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        //jConsole.append(content);
        //printStream.close();
     //}


    public JPanel createDemoPanel() {

        // create plot...
        NumberAxis xAxis = new NumberAxis("");
        xAxis.setAutoRangeIncludesZero(true);
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //xAxis.setAutoRange(true);
        NumberAxis yAxis = new NumberAxis("");
        yAxis.setAutoRangeIncludesZero(true);

        DecimalFormat format = new DecimalFormat("#.########");
        yAxis.setNumberFormatOverride(format);
        //yAxis.setTickUnit(new NumberTickUnit(2));
        //yAxis.setAutoRange(true);
        //xAxis.setLowerMargin(0);
        //xAxis.setLowerBound(0);
        XYSplineRenderer renderer1 = new XYSplineRenderer();
        renderer1.setSeriesShapesVisible(0, false);

        double size = 20.0;
        double delta = size / 2.0;
        //renderer1.setSeriesShape( 0, new Rectangle2D.Double( -1.0, -1.0, 20.0, 20.0 ) );
        Shape shape1 = new Rectangle2D.Double(-delta, -delta, size, size);
        renderer1.setSeriesShape(0, shape1);
        //renderer1.setSeriesVisible(1, Boolean.FALSE);
        XYPlot plot = new XYPlot(createSampleData(), xAxis, yAxis, renderer1);
        //plot.setRenderer( new CustomRenderer());
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        
        ValueMarker marker1 = new ValueMarker(0);
        marker1.setPaint(Color.black);
        //marker1.setLabelFont(new Font("Dialog", Font.BOLD, 39));
        plot.addRangeMarker(marker1);

        //plot.setDomainGridlinePaint(Color.lightGray);
        //plot.setDomainZeroBaselinePaint(Color.lightGray);
        //plot.setRangeGridlinePaint(Color.lightGray);
        //plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));

        // create and return the chart panel...
        JFreeChart chart = new JFreeChart(null,
                JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        //ChartUtilities.applyCurrentTheme(chart);
        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    private XYDataset createSampleData() {;
        series.add(0, 0.00000000);
        XYSeriesCollection result = new XYSeriesCollection(series);
        series2.add(0, 0);
        result.addSeries(series2);
        return result;
    }



    class AnswerWorker extends SwingWorker<Integer, Integer>
    {
        protected Integer doInBackground() throws Exception
        {
            while(running == true){
                Thread.sleep(1);
                diceBet();
            }

            
            Thread.sleep(1);
            return 42;
        }

        protected void done()
        {
            try
            {
                //JOptionPane.showMessageDialog(f, get());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
       }
    }

    
    public main() {
        initComponents();
        customComponents();
        
        //global_var.STDOUT = printStream;
    }
    private void customComponents() {
        jTabbedPane1.setEnabledAt(1, false);
        jTextField1.setVisible(false);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable3.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(120);

        PrintStream out = new PrintStream(new OutputStream() {
        @Override
            public void write(int b) throws IOException {
                jConsole.append(""+(char)(b & 0xFF));
            }
        });
        System.setOut(out);
        //System.setErr(out);
        global_var.STDOUT = out;


        jEditorLua = new RSyntaxTextArea();
        String basicLua = "chance = 49.5 --sets your chance for placing a bet\n" +
                    "nextbet = 0.00000000 --sets your first bet.\n" +
                    "bethigh = true --bet high when true, bet low when false\n" +
                    "--See Console for print output\n" +
                    "\n" +
                    "function dobet()\n" +
                    "	print(win)\n" +
                    "	print(lastBet.Roll)\n" +
                    "	bethigh = not bethigh\n" +
                    "	print(bethigh)\n" +
                    "end";
        jEditorLua.setText(basicLua);
        jEditorLua.setEnabled(false);
        jEditorLua.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA);
        jEditorLua.setCodeFoldingEnabled(true);
        RTextScrollPane jScrollPane2 = new RTextScrollPane(jEditorLua);

        jEditorPython = new RSyntaxTextArea();
        String basicPython = "chance = 49.5 --sets your chance for placing a bet\n" +
                    "nextbet = 0.00000000 --sets your first bet.\n" +
                    "bethigh = true --bet high when true, bet low when false\n" +
                    "\n" +
                    "def dobet():\n" +
                    "   print(\"dobet() running\")";
        jEditorPython.setText(basicPython);
        jEditorPython.setText("Python isn't available.");
        jEditorPython.setEnabled(false);
        jEditorPython.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        jEditorPython.setCodeFoldingEnabled(true);
        RTextScrollPane jScrollPane3 = new RTextScrollPane(jEditorPython);

  
        jEnableLuaCheck = new javax.swing.JCheckBox();
        jEnableLuaCheck.setText("Enable code (Lua)");

        jEnableLuaCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jEnableLuaCheckStateChanged(evt);
            }
        });

        jRunSimLuaButton = new javax.swing.JButton();
        jRunSimLuaButton.setText("Run simulation");
        jRunSimLuaButton.setEnabled(false);

        //javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        javax.swing.GroupLayout LuaLayout1 =  (javax.swing.GroupLayout) jPanel10.getLayout();
        //jPanel10.setLayout(LuaLayout1);
        LuaLayout1.setHorizontalGroup(
            LuaLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(LuaLayout1.createSequentialGroup()
                .addComponent(jRunSimLuaButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jEnableLuaCheck)
                .addGap(24, 24, 24))
        );
        LuaLayout1.setVerticalGroup(
            LuaLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LuaLayout1.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(LuaLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jEnableLuaCheck)
                    .addComponent(jRunSimLuaButton)))
        );

        

        jEnablePythonCheck = new javax.swing.JCheckBox();
        jEnablePythonCheck.setText("Enable code (Python)");

        jEnablePythonCheck.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jEnablePythonCheckStateChanged(evt);
            }
        });

        jRunSimPythonButton = new javax.swing.JButton();
        jRunSimPythonButton.setText("Run simulation");
        jRunSimPythonButton.setEnabled(false);

        //javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        javax.swing.GroupLayout PythonLayout1 =  (javax.swing.GroupLayout) jPanel11.getLayout();
        //jPanel11.setLayout(PythonLayout1);
        PythonLayout1.setHorizontalGroup(
            PythonLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(PythonLayout1.createSequentialGroup()
                .addComponent(jRunSimPythonButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jEnablePythonCheck)
                .addContainerGap())
        );
        PythonLayout1.setVerticalGroup(
            PythonLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PythonLayout1.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PythonLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jEnablePythonCheck)
                    .addComponent(jRunSimPythonButton)))
        );

        
        jConsole = new javax.swing.JTextArea();
        jConsole.setEditable(false);
        jConsole.setColumns(20);
        jConsole.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        jConsole.setRows(5);
        jScrollConsole = new javax.swing.JScrollPane();
        jScrollConsole.setViewportView(jConsole);

        jCommandInput = new javax.swing.JTextField();
        jCommandInput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCommandInput.setVisible(false);

        jLabelCmd = new javax.swing.JLabel();
        jLabelCmd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCmd.setText("command:");
        jLabelCmd.setVisible(false);
        //javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        javax.swing.GroupLayout ConsoleLayout1 =  (javax.swing.GroupLayout) jPanel12.getLayout();
        //jPanel12.setLayout(ConsoleLayout1);
        ConsoleLayout1.setHorizontalGroup(
            ConsoleLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollConsole, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
            .addGroup(ConsoleLayout1.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCmd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCommandInput))
        );
        ConsoleLayout1.setVerticalGroup(
            ConsoleLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConsoleLayout1.createSequentialGroup()
                .addComponent(jScrollConsole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ConsoleLayout1.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCommandInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCmd)))
        );
    }

    private void jEnableLuaCheckStateChanged(java.awt.event.ItemEvent evt) {                                            
        if(evt.getStateChange() == ItemEvent.SELECTED){
            jEditorLua.setEnabled(true);
            jButton1.setEnabled(true);

        } else {
            jEditorLua.setEnabled(false);
            jButton1.setEnabled(false);
            running = false;
        }
    }          

    private void jEnablePythonCheckStateChanged(java.awt.event.ItemEvent evt) {                                            
        if(evt.getStateChange() == ItemEvent.SELECTED){
            //jEditorPython.setEnabled(true);
            //jButton1.setEnabled(true);

        } else {
            //jEditorPython.setEnabled(false);
            //jButton1.setEnabled(false);
        }
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
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel18 = createDemoPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelTotalProfit = new javax.swing.JLabel();
        labelTotalBets = new javax.swing.JLabel();
        labelTotalWin = new javax.swing.JLabel();
        labelTotalLosses = new javax.swing.JLabel();
        labelTotalWagered = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        labelCurrentStreak = new javax.swing.JLabel();
        labelBestWinstreak = new javax.swing.JLabel();
        labelWorstLosestreak = new javax.swing.JLabel();
        labelTimeRunning = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        labelHighestBet = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel9 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("0000000#7073");
        setPreferredSize(new java.awt.Dimension(1020, 660));

        jLabel1.setText("Status:");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTextField1.setEditable(false);
        jTextField1.setText("0");
        jTextField1.setEnabled(false);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "stake.com", "stake.games", "stake.bet", "staketr.com", "staketr2.com", "staketr3.com" }));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "btc", "eth", "ltc", "doge", "bch", "xrp", "trx", "eos" }));

        jButton2.setText("Shut down");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Run it");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jSplitPane1.setDividerLocation(770);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "total profit", "current", "bet", "Multiplier", "payout", "result", "condition", "target", "user", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel18.setPreferredSize(new java.awt.Dimension(500, 190));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Stats"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Total profit:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Bets:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Wins:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Losses:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Wagered:");

        labelTotalProfit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTotalProfit.setText("-");

        labelTotalBets.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTotalBets.setText("-");

        labelTotalWin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTotalWin.setForeground(new java.awt.Color(51, 204, 0));
        labelTotalWin.setText("-");

        labelTotalLosses.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTotalLosses.setForeground(new java.awt.Color(255, 0, 51));
        labelTotalLosses.setText("-");

        labelTotalWagered.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTotalWagered.setText("-");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Current streak:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Best Win streak:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Worst lose streak:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Time running:");

        labelCurrentStreak.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelCurrentStreak.setText("-");

        labelBestWinstreak.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelBestWinstreak.setForeground(new java.awt.Color(0, 204, 0));
        labelBestWinstreak.setText("-");

        labelWorstLosestreak.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelWorstLosestreak.setForeground(new java.awt.Color(255, 0, 51));
        labelWorstLosestreak.setText("-");

        labelTimeRunning.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTimeRunning.setText("-");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Highest bet:");

        labelHighestBet.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelHighestBet.setText("-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(labelTotalProfit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(labelTotalWagered)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(labelTotalLosses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(labelTotalWin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(labelTotalBets)
                        .addGap(124, 124, 124)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCurrentStreak)
                    .addComponent(labelBestWinstreak)
                    .addComponent(labelWorstLosestreak)
                    .addComponent(labelTimeRunning)
                    .addComponent(labelHighestBet))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(labelTotalProfit)
                    .addComponent(jLabel9)
                    .addComponent(labelHighestBet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labelTotalBets)
                    .addComponent(jLabel14)
                    .addComponent(labelCurrentStreak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelTotalWin)
                    .addComponent(jLabel15)
                    .addComponent(labelBestWinstreak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labelTotalLosses)
                    .addComponent(jLabel16)
                    .addComponent(labelWorstLosestreak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelTotalWagered)
                    .addComponent(jLabel17)
                    .addComponent(labelTimeRunning))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        labelTotalProfit.getAccessibleContext().setAccessibleName("");
        labelTotalBets.getAccessibleContext().setAccessibleName("labelBetMade");
        labelTotalWin.getAccessibleContext().setAccessibleName("labelTotalWin");
        labelTotalLosses.getAccessibleContext().setAccessibleName("labelLosses");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Lua", jPanel10);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Python", jPanel11);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Console", jPanel12);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addComponent(jTabbedPane3)
                    .addContainerGap()))
        );

        jSplitPane1.setRightComponent(jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        jTabbedPane1.addTab("Dice", jPanel1);

        jSplitPane2.setDividerLocation(770);

        jPanel17.setPreferredSize(new java.awt.Dimension(0, 184));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        jPanel16.setPreferredSize(new java.awt.Dimension(0, 196));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 191, Short.MAX_VALUE)
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "total profit", "current", "bet", "Multiplier", "payout", "result", "target", "user", "Date"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jSplitPane2.setLeftComponent(jPanel14);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Lua", jPanel9);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Python", jPanel21);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 458, Short.MAX_VALUE)
        );

        jTabbedPane5.addTab("Console", jPanel22);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jTabbedPane5)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel8);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        jTabbedPane1.addTab("Limbo", jPanel13);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "System", "Metal", "Motif", "Nimbus" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Theme:");

        jLabel7.setText("Rows:");

        jTextField2.setText("10");
        jTextField2.setToolTipText("");
        jTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (jTextField2.getText().length() >= 2 ) // limit to 3 characters
                e.consume();
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2)))
                .addContainerGap(779, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(387, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("GUI", jPanel7);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "stop()", "resetstats()", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel10.setText("Lua functions");

        jLabel11.setText("Lua variables");

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "chance", "bethigh", "nextbet", "currency", "win", "lastBet.Roll", "profit", "currentstreak", "currentprofit", "wagered", "previousbet", "bets", "wins", "losses", " ", " ", " ", " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(467, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap(214, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("INFO", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Settings", jPanel4);

        jLabel12.setText("Api key:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
        );

        getAccessibleContext().setAccessibleName("jFrame1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(running == false){
            running = true;
            sitemirror = jComboBox2.getSelectedItem().toString();
            //betamount = parseDouble(jTextField1.getText());
            apikey = jPasswordField1.getText();
            var_currency = jComboBox1.getSelectedItem().toString();
            
            startDate = (int) System.currentTimeMillis();
            try {
                 rowCountTable = Integer.parseInt(jTextField2.getText());// output = 25
            } catch (NumberFormatException ex){
                 System.out.println("Please set a vaild number for rows");
                 running = false;
            }
            global_var.set("currency", var_currency);
            SetLuaVariables();
            String script = jEditorLua.getText();
            try {
                global_var.load(script).call();
                

            } catch(Exception e) {
                System.out.println(e);
                running = false;
                //jConsole.append("\n" + e.toString() + "\n");

            }
            new AnswerWorker().execute();
        }
        //int delay = 1; //milliseconds
        //ActionListener taskPerformer = new ActionListener() {
            //public void actionPerformed(ActionEvent evt) {
                //randomness();
            //}
        //};
        //new Timer(delay, taskPerformer).start();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        running = false;        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        int index = jComboBox3.getSelectedIndex();
        String plaf = "";
        if (index == 0) {
            plaf = javax.swing.UIManager.getSystemLookAndFeelClassName();
        } else if (index == 1) {
            plaf = "javax.swing.plaf.metal.MetalLookAndFeel";
        } else if (index == 2) {
            plaf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        } else if (index == 3) {
            plaf = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        } 
        try {
            javax.swing.UIManager.setLookAndFeel(plaf);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(this); 
        this.pack();
    }//GEN-LAST:event_jComboBox3ActionPerformed
    
    public void SetLuaVariables(){

        try {
      
            //global_var.set("balance", var_balance);
            global_var.set("profit", profit);
            global_var.set("currentstreak", var_currentstreak);
            global_var.set("previousbet", var_previousbet);
            global_var.set("bets", bets);
            global_var.set("wins", var_wins);
            global_var.set("losses", var_losses);
            global_var.set("wagered", var_wagered);
            //global_var.set("win", win.toString());
            global_var.set("currentprofit", var_currentprofit);
            //global_var.set("currency", var_currency);
            LuaValue instanceWin = CoerceJavaToLua.coerce(win);
            global_var.set("win", instanceWin);
            LuaValue instanceHigh = CoerceJavaToLua.coerce(var_bethigh);
            global_var.set("bethigh", instanceHigh);
            LuaValue instanceBet = CoerceJavaToLua.coerce(new lastBet());
            global_var.set("lastBet", instanceBet);  

            LuaValue instanceReset = CoerceJavaToLua.coerce(new ResetStats());
            global_var.set("resetstats", instanceReset); 
            LuaValue instanceStop = CoerceJavaToLua.coerce(new StopRun());
            global_var.set("stop", instanceStop); 

            //LuaValue instancePrint = CoerceJavaToLua.coerce(new Print());
            //global_var.set("print", instancePrint);

        
        } catch(Exception e) {
            System.out.println(e);
            running = false;
            //jConsole.append("\n" + e.toString() + "\n");

        }
        
    }      



    public void GetLuaVariables(){


        try {

            LuaValue bethigh_lua = global_var.get("bethigh");
            var_bethigh = bethigh_lua.toboolean();
            LuaValue betamount_lua = global_var.get("nextbet");
            betamount = betamount_lua.todouble();
            LuaValue chance_lua = global_var.get("chance");
            var_chance = chance_lua.todouble();
            LuaValue currency_lua = global_var.get("currency");
            var_currency = currency_lua.toString();
            if (var_currency.equals("nil")){
                //var_currency = jComboBox1.getSelectedItem().toString();
            }
            if (betamount == 0){
                //betamount = parseDouble(jTextField1.getText());
            }
            //System.out.println(betamount);
        
        } catch(Exception e) {
            System.out.println(e);
            running = false;
            //jConsole.append("\n" + e.toString() + "\n");

        }

        
        //PrintToStringFromLuaj();
        

    }

    public void DoBet() {
        try {

            LuaValue dobet = global_var.get("dobet").call();
        
        } catch(Exception e) {
            System.out.println(e);
            running = false;
            //jConsole.append("\n" + e.toString() + "\n");

        }
        
    }

    public void setTarget(double chance, Boolean high){
        if(chance <= 99.99 && chance >= 0.01){
            if(high == true){
                nexttarget = 100 - chance;
                condition = "above";
            } else {
                nexttarget = chance;
                condition = "below";            
            }
        }
        else
        {
            System.out.println("Set a chance higher or equal to 0.01 and, lower or equal to 99.99");
            running = false;
        }
    }


    public void diceBet(){
        
        
        

        GetLuaVariables();
        setTarget(var_chance, var_bethigh);
        try {
            if(running == true)
            {

                JSONObject vars = new JSONObject();
                vars.put("currency", var_currency);
                vars.put("amount", betamount);
                vars.put("target", nexttarget);   
                vars.put("condition", condition); 
                vars.put("identifier", getAlphaNumericString(20));          
                JSONObject query = new JSONObject();
                query.put("operationName", "DiceRoll");
                query.put("variables", vars);
                query.put("query", "mutation DiceRoll($amount: Float!, $target: Float!, $condition: CasinoGameDiceConditionEnum!, $currency: CurrencyEnum!, $identifier: String!) {\n  diceRoll(amount: $amount, target: $target, condition: $condition, currency: $currency, identifier: $identifier) {\n    ...CasinoBetFragment\n    state {\n      ...DiceStateFragment\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment CasinoBetFragment on CasinoBet {\n  id\n  active\n  payoutMultiplier\n  amountMultiplier\n  amount\n  payout\n  updatedAt\n  currency\n  game\n  user {\n    id\n    name\n    __typename\n  }\n  __typename\n}\n\nfragment DiceStateFragment on CasinoGameDice {\n  result\n  target\n  condition\n  __typename\n}\n");

                start_epos = (int) System.currentTimeMillis();
                String response =  Utility.excutePost("https://api." + sitemirror + "/graphql", query.toString(), apikey);
                final_epos = (int) System.currentTimeMillis();                
                //System.out.println(response);
                //JsonObject data = new JsonParser().parse(response).getAsJsonObject();
                JSONObject json = new JSONObject(response);
                if (json.has("errors")){

                    JSONArray errors = json.getJSONArray("errors");
                    JSONObject rec = errors.getJSONObject(0);
                    String errmessage = rec.getString("message");
                    String errorType = rec.getString("errorType");
                    System.out.println("\n" + errmessage + " type:" + errorType + "\n");
                    if(errorType.equals("insufficientBalance")  || errorType.equals("invalidSession") || errorType.equals("notAuthenticated") || errorType.equals("insignificantBet")){
                        running = false;
                    }

                } else {





                    JSONObject data = json.getJSONObject("data");
                    JSONObject diceRoll = data.getJSONObject("diceRoll");
                    JSONObject state = diceRoll.getJSONObject("state");
                    JSONObject user = diceRoll.getJSONObject("user");
                    String username = user.getString("name");
                    String current_condition = state.getString("condition");
                    double current_result = parseDouble(state.getString("result"));
                    double current_target = parseDouble(state.getString("target"));
                    String updated = diceRoll.getString("updatedAt");
                    String current_game = diceRoll.getString("game");
                    double payout = parseDouble(diceRoll.getString("payout"));
                    double amount = parseDouble(diceRoll.getString("amount"));
                    double payoutMultiplier = parseDouble(diceRoll.getString("payoutMultiplier"));
                    String current_currency = diceRoll.getString("currency");

                    String current_income = "";
                    bets++;
                    if(payoutMultiplier == 0){
                        win = false;
                        winstreak = 0;
                        losestreak++;
                        var_losses++;
                        maxlosestreak.add(losestreak);
                        maxlosestreak.add(0);
                        //Integer maxlosses = Collections.max(maxlosestreak);
                        //maxlosestreak.add(maxlosses);
                        profit -= amount;
                        var_currentprofit = -amount;
                        current_income = "-" + format8.format(amount);
                        java.awt.Color redColor = new java.awt.Color(255,102,102);
                        colouringTable.setColors(redColor);
                    } else {
                        win = true;
                        winstreak++;
                        losestreak = 0;
                        maxwinstreak.add(winstreak);
                        maxwinstreak.add(0);
                        //Integer maxwin = Collections.max(maxwinstreak);
                        //maxwinstreak.add(maxwin);
                        var_wins++;
                        profit += payout - amount;
                        var_currentprofit = payout - amount;
                        current_income = "+" + format8.format(payout - amount);
                        java.awt.Color greenColor = new java.awt.Color(102,255,102);
                        colouringTable.setColors(greenColor);
                    }
                    if(losestreak > winstreak){
                        labelCurrentStreak.setText(losestreak.toString());
                        labelCurrentStreak.setForeground(Color.red);
                        var_currentstreak = -losestreak;
                    } else {
                        labelCurrentStreak.setText(winstreak.toString());
                        labelCurrentStreak.setForeground(Color.green);
                        var_currentstreak = winstreak;
                    }
                    maxhighestbet.add(0.000000);
                    maxhighestbet.add(amount);
                    


                    var_wagered += amount;
                    var_previousbet = amount;
                    lastBet.Roll = current_result;

                    labelTotalProfit.setText(format8.format(profit));
                    Integer totalbet = var_wins + var_losses;
                    labelTotalBets.setText(totalbet.toString());
                    labelTotalWin.setText(var_wins.toString());
                    labelTotalLosses.setText(var_losses.toString());

                    labelTotalWagered.setText(format8.format(var_wagered));
                    try {
                        labelBestWinstreak.setText(Collections.max(maxwinstreak).toString());
                        labelWorstLosestreak.setText(Collections.max(maxlosestreak).toString());
                        labelHighestBet.setText(format8.format(Collections.max(maxhighestbet)));
                        Double maxbet = Collections.max(maxhighestbet);
                        Integer maxloss = Collections.max(maxlosestreak);
                        Integer maxwins = Collections.max(maxwinstreak);
                        maxhighestbet.clear();
                        maxwinstreak.clear();
                        maxlosestreak.clear();

                        maxhighestbet.add(maxbet);
                        maxwinstreak.add(maxwins);
                        maxlosestreak.add(maxloss);
                    } catch (Exception e) {
                        //System.out.println(e);
                    }
                    //System.out.println(maxwinstreak);
                    //System.out.println(maxlosestreak);
                    //System.out.println(maxhighestbet);
                    labelTimeRunning.setText(TotalRunning());

                    Integer speedepos = final_epos - start_epos;
                    jLabel1.setText("Status: " + format1.format(1000 / speedepos) + " bets/sec");


                    SetLuaVariables();
                    DoBet();
                        //GetLuaVariables();



                    series.add(bets, profit);
                    //String log_text = bets + "." + current_game + " | profit: " + format8.format(profit) + " | " + current_income + " | amount: " + format8.format(amount) + " " + current_currency + " | payoutMultiplier: " + format4.format(payoutMultiplier) + " | payout: " + format8.format(payout) + " | result: " + format2.format(current_result) + " '" + current_condition + "' " + format2.format(current_target) + " | user: " + username + ".";
                    AddRow(bets + "." + current_game, format8.format(profit), current_income, format8.format(amount) + " " + current_currency, format4.format(payoutMultiplier), format8.format(payout), format2.format(current_result), current_condition, format2.format(current_target), username, updated);
                    for (int i = 0; i < jTable3.getColumnCount(); i++){
                        jTable3.getColumnModel().getColumn(i).setCellRenderer(colouringTable);
                    }
                }
                jConsole.setCaretPosition(jConsole.getDocument().getLength());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void AddRow(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11){
        DefaultTableModel yourModel = (DefaultTableModel) jTable3.getModel();
        yourModel.insertRow(0, new Object[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
        if(yourModel.getRowCount() > rowCountTable){
            yourModel.removeRow(yourModel.getRowCount() - 1);
        }    
    }
    
    class CustomRenderer extends DefaultTableCellRenderer 
    {
        private List<Color> desiredColors = new ArrayList<Color>();

        public void setColors(Color incomingColor)
        {
            desiredColors.add(0, incomingColor);
            if(desiredColors.size() > rowCountTable){
                desiredColors.remove(desiredColors.size()- 1);
            }
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            for (int i = 0; i < desiredColors.size(); i++) {
                if(row == i){
                    cellComponent.setBackground(desiredColors.get(i));
                }
            }
            return cellComponent;
        }
    }



    public class StopRun extends ZeroArgFunction {
        @Override
        public LuaValue call() {
            // Return a function compiled from an in-line script
            running = false;
            LuaValue ret = global_var.get("bethigh");
            System.out.println("stop() were called.");
            return ret;
        }
    }

    public class ResetStats extends ZeroArgFunction {
        @Override
        public LuaValue call() {
            // Return a function compiled from an in-line script
            profit = 0.00000000;
            var_currentstreak = 0;
            var_wins = 0;
            var_losses = 0;
            var_wagered = 0;
            winstreak = 0;
            losestreak = 0;
            maxwinstreak.clear();
            maxlosestreak.clear();
            maxhighestbet.clear();
            LuaValue ret = global_var.get("bethigh");
            System.out.println("Stats were reset.");
            return ret;
        }
    }




    public String TotalRunning() {

        int diff = (int) System.currentTimeMillis() - startDate;
        int seconds = (int) floor((diff / 1000) % 60);
        int minutes = (int) floor((diff / (1000 * 60)) % 60);
        int hours = (int) floor((diff / (1000 * 60 * 60)) % 24);

        String currentTime =
            hours + "h : " + minutes + " min : " + seconds + " sec";
        return currentTime;
    }

    public int maxValue(int array[]){
        int max = Arrays.stream(array).max().getAsInt();
        return max;
    }
    // function to generate a random string of length n
    static String getAlphaNumericString(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "_-ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "_-0123456789"
                                    + "_-abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
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
            //javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            //javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            

         
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }
    private RSyntaxTextArea jEditorLua;
    private RSyntaxTextArea jEditorPython;
    private javax.swing.JCheckBox jEnableLuaCheck;
    private javax.swing.JCheckBox jEnablePythonCheck;
    private javax.swing.JButton jRunSimLuaButton;
    private javax.swing.JButton jRunSimPythonButton;
    public javax.swing.JTextArea jConsole;
    private javax.swing.JScrollPane jScrollConsole;
    private javax.swing.JTextField jCommandInput;
    private javax.swing.JLabel jLabelCmd;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelBestWinstreak;
    private javax.swing.JLabel labelCurrentStreak;
    private javax.swing.JLabel labelHighestBet;
    private javax.swing.JLabel labelTimeRunning;
    private javax.swing.JLabel labelTotalBets;
    private javax.swing.JLabel labelTotalLosses;
    private javax.swing.JLabel labelTotalProfit;
    private javax.swing.JLabel labelTotalWagered;
    private javax.swing.JLabel labelTotalWin;
    private javax.swing.JLabel labelWorstLosestreak;
    // End of variables declaration//GEN-END:variables
}
