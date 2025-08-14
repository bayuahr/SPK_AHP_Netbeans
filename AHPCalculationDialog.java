
// package application.views;
// import application.dao.CandidateDao;
// import application.dao.SelectionDao;
// import application.daoimpl.CandidateDaoImpl;
// import application.models.CandidateModel;
// import application.daoimpl.SelectionDaoImpl;
// import application.models.SelectionModel;
// import application.models.AlternativeWeightModel;
// import application.models.AlternativeWeightTableModel;
// import application.utils.AHPCalculation;
// import java.awt.Color;
// import java.text.DecimalFormat;
// import java.util.ArrayList;
// import java.util.List;
// import javax.swing.JOptionPane;
// import javax.swing.JRootPane;
// import javax.swing.RowSorter;
// import javax.swing.SortOrder;
// import javax.swing.table.TableModel;
// import javax.swing.table.TableRowSorter;

// /**
//  *
//  * @author mahasiswa unindra 
//  */
// public class AHPCalculationDialog extends javax.swing.JDialog {
//     protected AHPCalculation ahpCalculation = new AHPCalculation();
//     DecimalFormat df = new DecimalFormat("0.000");
//     DecimalFormat df2 = new DecimalFormat("0.000");
//     private final CandidateDao candidateDao;
//     private final SelectionDao selectionDao;
//     private CandidateModel candidateFound;
//     private List<AlternativeWeightModel> alternativeWeightModel = null; 
    
//     /**
//      * Creates new form AHPCalculationDialog
//      */
//     public AHPCalculationDialog(java.awt.Frame parent, boolean modal) {
//         super(parent, modal);
//         initComponents();
        
//         //add Panel, add panel(sebuah panel)
//         Pane.add(PanelPerhitungan);
//         Pane.repaint();
//         Pane.revalidate();
        
//         candidateDao = new CandidateDaoImpl();
//         selectionDao = new SelectionDaoImpl();
        
//         for (CandidateModel item : candidateDao.findAll()) {
//             cbIdCalonPelamar.addItem(String.valueOf(item.getId()));
//         }
//         mulaiHitung.setBackground(Color.white);
//         Simpan.setBackground(Color.white);
        
//     }
    
//     void clearForm(){
//         textFieldTotalNilai.setText("");
//     }
    
//     private void calculateComparisonCriteriaMatrix(){
//         try {
//             List<CandidateModel> candidatesFound = this.candidateDao.findAll();
            
//             int size = candidatesFound.size();
            
            
// //            List<AlternativeWeightModel> alternativeWeight = new ArrayList<>();
            
//             Object[][] alternativeWeight = new Object[size][7];

//             int[][] criterias = new int[4][size];
            
//             for (int row = 0; row < 4; row++) {
//                 for (int col = 0; col < size; col++) {
//                     switch (row) {
//                         case 0:
//                             criterias[row][col] = candidatesFound.get(col).getLeadershipScore();
//                             break;
//                         case 1:
//                             criterias[row][col] = candidatesFound.get(col).getKnowledgeScore();
//                             break;
//                         case 2:
//                             criterias[row][col] = candidatesFound.get(col).getTechnicalSkillScore();
//                             break;
//                         default:
//                             criterias[row][col] = candidatesFound.get(col).getAdvancedSkillScore();
//                             break;
//                     }
//                 }
//             }
            
//             System.out.println("\n[#" + "LOOP ALTERNATIF TERHADAP MASING-MASING KRITERIA" +"]");
//             for (int criteria = 0; criteria < criterias.length; criteria++) {
                
//                 switch (criteria) {
//                     case 0:
//                         System.out.println("Perhitungan Alternatif Terhadap Masing-masing Kriteria Nilai Kepemimpinan");
//                         break;
//                     case 1:
//                         System.out.println("Perhitungan Alternatif Terhadap Masing-masing Kriteria Nilai Pengetahuan");
//                         break;
//                     case 2:
//                         System.out.println("Perhitungan Alternatif Terhadap Masing-masing Kriteria Nilai Kemampuan Teknis");
//                         break;
//                     default:
//                         System.out.println("Perhitungan Alternatif Terhadap Masing-masing Kriteria Nilai Kemampuan Lanjutan");
//                         break;
//                 }
                
//                 int[] scores = criterias[criteria];
                
//                 double[][] alternativeScoreMatrix = new double[size][size];
                
//                 double[] totalColumnAlternativeScoreMatrix = new double[size];
                
//                 // Mengisi matriks perbandingan pasangan
//                 for (int row = 0; row < size; row++) {
//                     for (int col = 0; col < size; col++) {
//                         alternativeScoreMatrix[row][col] = (double) scores[row] / scores[col];
//                         totalColumnAlternativeScoreMatrix[col] += alternativeScoreMatrix[row][col];
//                     }
//                 }
            
//                 for (double num : totalColumnAlternativeScoreMatrix) {
//                     System.out.print(num + " ");
//                 }
            
//                 printArray2D(alternativeScoreMatrix, "RES alternativeScoreMatrix");

//                 double[][] normalizedAlternativeScoreMatrix = new double[size][size];
//                 double[] normalizedAlternativeScoreMatrixSum = new double[size];
//                 double[] priorityVector = new double[size];

//                 // Calculate the normalized matrix and column sums
//                 for (int row = 0; row < alternativeScoreMatrix.length; row++) {
//                     for (int col = 0; col < alternativeScoreMatrix.length; col++) {
//                         normalizedAlternativeScoreMatrix[row][col] = alternativeScoreMatrix[row][col] / totalColumnAlternativeScoreMatrix[col];
//                         normalizedAlternativeScoreMatrixSum[row] += normalizedAlternativeScoreMatrix[row][col];
//                         priorityVector[row] = normalizedAlternativeScoreMatrixSum[row] / size; // atau rata-rata
//                     }
//                 }

//                 printArray2D(normalizedAlternativeScoreMatrix, "RES normalizedAlternativeScoreMatrix");

//                 for (double num : priorityVector) {
//                     System.out.println(num + " === ");
//                 }
                
//                 for (int i = 0; i < size; i++) {
//                     if(criteria == 0){
//                        alternativeWeight[i][0] = candidatesFound.get(i).getId();
//                        alternativeWeight[i][1] = candidatesFound.get(i).getName();
//                     }
                    
//                     for (int p = 0; p < priorityVector.length; p++) {
//                         switch (criteria) {
//                             case 0:
//                                 alternativeWeight[p][2] = priorityVector[p];
//                                 break;
//                             case 1:
//                                 alternativeWeight[p][3] = priorityVector[p];
//                                 break;
//                             case 2:
//                                 alternativeWeight[p][4] = priorityVector[p];
//                                 break;
//                             case 3:
//                                 alternativeWeight[p][5] = priorityVector[p];
//                                 break;
//                             default:
//                                 break;
//                         }
//                     }

//                 }
//             }
            

//             double[] priorityCriteria = ahpCalculation.getPriorityVector();
            
//             for (int row = 0; row < alternativeWeight.length; row++) {
//                 double[] scoreCriteria = new double[4];
//                 for (int col = 0; col < alternativeWeight[row].length; col++) {
//                     if(col > 1 && col <= 5){
//                         scoreCriteria[col - 2] = (double) alternativeWeight[row][col];
//                     }
//                 }
//                 alternativeWeight[row][6] = calculateScore(priorityCriteria, scoreCriteria);
//                 System.out.println();
//             }
            
                        
//             for (int row = 0; row < alternativeWeight.length; row++) {
//                 double[] scoreCriteria = new double[4];
//                 for (int col = 0; col < alternativeWeight[row].length; col++) {
//                     System.out.print(alternativeWeight[row][col] + " ");
//                 }
//                 System.out.println();
//             }
            
//             this.alternativeWeightModel = AlternativeWeightModel.fromArray(alternativeWeight);
            
//             loadTable(alternativeWeightModel);
//         }catch(Exception e){
//             JOptionPane.showMessageDialog(null,e);
//         }
//     }
    
//     public double calculateScore(double[] weights, double[] values) {
//         double score = 0.0;
//         for (int i = 0; i < weights.length; i++) {
//             score += weights[i] * values[i];
//         }
//         return score;
//     }
    
//     public void printArray2D(double[][] array, String title) {
//         System.out.println("\n[#" + title +"]");
//         for (double[] array1 : array) {
//             for (int j = 0; j < array1.length; j++) {
//                 System.out.print(array1[j] + " ");
//             }
//             System.out.println();
//         }
//     }
    
//     public void loadTable(List<AlternativeWeightModel> list) {
//         AlternativeWeightTableModel alternativeWeightTableModel = new AlternativeWeightTableModel(list);
        
//         jTable1.setModel(alternativeWeightTableModel);
        
//         TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable1.getModel());
//         jTable1.setRowSorter(sorter);

//         List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
//         sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
//         sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
//         sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
//         sorter.setSortKeys(sortKeys);
//     }
    
//     /**
//      * This method is called from within the constructor to initialize the form.
//      * WARNING: Do NOT modify this code. The content of this method is always
//      * regenerated by the Form Editor.
//      */
//     @SuppressWarnings("unchecked")
//     // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//     private void initComponents() {

//         btnG = new javax.swing.ButtonGroup();
//         PanelPerhitungan = new javax.swing.JPanel();
//         judul = new javax.swing.JLabel();
//         jScrollPane1 = new javax.swing.JScrollPane();
//         jPanel1 = new javax.swing.JPanel();
//         mulaiHitung = new javax.swing.JButton();
//         jSeparator1 = new javax.swing.JSeparator();
//         jPanel2 = new javax.swing.JPanel();
//         jLabel2 = new javax.swing.JLabel();
//         jLabel3 = new javax.swing.JLabel();
//         jLabel4 = new javax.swing.JLabel();
//         jLabel5 = new javax.swing.JLabel();
//         jLabel6 = new javax.swing.JLabel();
//         k1k1 = new javax.swing.JTextField();
//         k1k2 = new javax.swing.JTextField();
//         k1k3 = new javax.swing.JTextField();
//         k1k4 = new javax.swing.JTextField();
//         k2k1 = new javax.swing.JTextField();
//         k2k2 = new javax.swing.JTextField();
//         k2k3 = new javax.swing.JTextField();
//         k2k4 = new javax.swing.JTextField();
//         k3k1 = new javax.swing.JTextField();
//         k3k2 = new javax.swing.JTextField();
//         k3k3 = new javax.swing.JTextField();
//         k3k4 = new javax.swing.JTextField();
//         k4k1 = new javax.swing.JTextField();
//         k4k2 = new javax.swing.JTextField();
//         k4k3 = new javax.swing.JTextField();
//         k4k4 = new javax.swing.JTextField();
//         jLabel7 = new javax.swing.JLabel();
//         jLabel8 = new javax.swing.JLabel();
//         jLabel9 = new javax.swing.JLabel();
//         jLabel10 = new javax.swing.JLabel();
//         jPanel3 = new javax.swing.JPanel();
//         jLabel11 = new javax.swing.JLabel();
//         jLabel12 = new javax.swing.JLabel();
//         jLabel13 = new javax.swing.JLabel();
//         jLabel14 = new javax.swing.JLabel();
//         jLabel15 = new javax.swing.JLabel();
//         k1k1N = new javax.swing.JTextField();
//         k1k2N = new javax.swing.JTextField();
//         k1k3N = new javax.swing.JTextField();
//         k1k4N = new javax.swing.JTextField();
//         k2k1N = new javax.swing.JTextField();
//         k2k2N = new javax.swing.JTextField();
//         k2k3N = new javax.swing.JTextField();
//         k2k4N = new javax.swing.JTextField();
//         k3k1N = new javax.swing.JTextField();
//         k3k2N = new javax.swing.JTextField();
//         k3k3N = new javax.swing.JTextField();
//         k3k4N = new javax.swing.JTextField();
//         k4k1N = new javax.swing.JTextField();
//         k4k2N = new javax.swing.JTextField();
//         k4k3N = new javax.swing.JTextField();
//         k4k4N = new javax.swing.JTextField();
//         jLabel16 = new javax.swing.JLabel();
//         jLabel17 = new javax.swing.JLabel();
//         jLabel18 = new javax.swing.JLabel();
//         jLabel19 = new javax.swing.JLabel();
//         jLabel20 = new javax.swing.JLabel();
//         Prior1 = new javax.swing.JTextField();
//         Prior2 = new javax.swing.JTextField();
//         Prior3 = new javax.swing.JTextField();
//         Prior4 = new javax.swing.JTextField();
//         Simpan = new javax.swing.JButton();
//         jScrollPane2 = new javax.swing.JScrollPane();
//         jTable1 = new javax.swing.JTable();
//         Pane = new javax.swing.JPanel();

//         PanelPerhitungan.setMinimumSize(new java.awt.Dimension(945, 525));

//         judul.setBackground(new java.awt.Color(51, 51, 51));
//         judul.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
//         judul.setForeground(new java.awt.Color(255, 255, 255));
//         judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//         judul.setText("Perhitungan Hasil Penilaian Calon Pelamar Menggunakan Metode AHP");
//         judul.setOpaque(true);

//         jPanel1.setBackground(new java.awt.Color(255, 255, 255));

//         mulaiHitung.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
//         mulaiHitung.setForeground(new java.awt.Color(179, 30, 144));
//         mulaiHitung.setText("Mulai Hitung");
//         mulaiHitung.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 210, 226)));
//         mulaiHitung.addMouseListener(new java.awt.event.MouseAdapter() {
//             public void mouseEntered(java.awt.event.MouseEvent evt) {
//                 mulaiHitungMouseEntered(evt);
//             }
//             public void mouseExited(java.awt.event.MouseEvent evt) {
//                 mulaiHitungMouseExited(evt);
//             }
//         });
//         mulaiHitung.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 mulaiHitungActionPerformed(evt);
//             }
//         });

//         jLabel2.setText("Matriks Perbandingan Kriteria");

//         jLabel3.setText("K1");

//         jLabel4.setText("K2");

//         jLabel5.setText("K3");

//         jLabel6.setText("K4");

//         k1k1.setEditable(false);
//         k1k1.setBackground(new java.awt.Color(245, 247, 250));
//         k1k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k2.setEditable(false);
//         k1k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k3.setEditable(false);
//         k1k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k4.setEditable(false);
//         k1k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k1.setEditable(false);
//         k2k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k2.setEditable(false);
//         k2k2.setBackground(new java.awt.Color(245, 247, 250));
//         k2k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k3.setEditable(false);
//         k2k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k4.setEditable(false);
//         k2k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k1.setEditable(false);
//         k3k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k2.setEditable(false);
//         k3k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k3.setEditable(false);
//         k3k3.setBackground(new java.awt.Color(245, 247, 250));
//         k3k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k4.setEditable(false);
//         k3k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k1.setEditable(false);
//         k4k1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k2.setEditable(false);
//         k4k2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k3.setEditable(false);
//         k4k3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k4.setEditable(false);
//         k4k4.setBackground(new java.awt.Color(245, 247, 250));
//         k4k4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         jLabel7.setText("K1");

//         jLabel8.setText("K2");

//         jLabel9.setText("K3");

//         jLabel10.setText("K4");

//         javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
//         jPanel2.setLayout(jPanel2Layout);
//         jPanel2Layout.setHorizontalGroup(
//             jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel2Layout.createSequentialGroup()
//                 .addGap(22, 22, 22)
//                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                     .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                         .addGroup(jPanel2Layout.createSequentialGroup()
//                             .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                 .addGroup(jPanel2Layout.createSequentialGroup()
//                                     .addComponent(jLabel4)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                     .addComponent(k2k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                     .addComponent(k2k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                 .addGroup(jPanel2Layout.createSequentialGroup()
//                                     .addComponent(jLabel5)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                     .addComponent(k3k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                     .addComponent(k3k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                 .addGroup(jPanel2Layout.createSequentialGroup()
//                                     .addComponent(jLabel6)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                     .addComponent(k4k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                     .addComponent(k4k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                 .addGroup(jPanel2Layout.createSequentialGroup()
//                                     .addComponent(jLabel3)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                     .addComponent(k1k1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                     .addComponent(k1k2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                             .addGap(10, 10, 10)
//                             .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                 .addComponent(k1k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k2k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k3k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k4k3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                             .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                             .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                 .addComponent(k1k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k2k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k3k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                 .addComponent(k4k4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                         .addGroup(jPanel2Layout.createSequentialGroup()
//                             .addGap(29, 29, 29)
//                             .addComponent(jLabel7)
//                             .addGap(40, 40, 40)
//                             .addComponent(jLabel8)
//                             .addGap(38, 38, 38)
//                             .addComponent(jLabel9)
//                             .addGap(42, 42, 42)
//                             .addComponent(jLabel10)))
//                     .addGroup(jPanel2Layout.createSequentialGroup()
//                         .addComponent(jLabel2)
//                         .addGap(102, 102, 102)))
//                 .addContainerGap(46, Short.MAX_VALUE))
//         );
//         jPanel2Layout.setVerticalGroup(
//             jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel2Layout.createSequentialGroup()
//                 .addContainerGap()
//                 .addComponent(jLabel2)
//                 .addGap(18, 18, 18)
//                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                     .addComponent(jLabel7)
//                     .addComponent(jLabel8)
//                     .addComponent(jLabel9)
//                     .addComponent(jLabel10))
//                 .addGap(5, 5, 5)
//                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addGroup(jPanel2Layout.createSequentialGroup()
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k1k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel3))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k2k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel4))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k3k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel5))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k4k1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel6)))
//                     .addGroup(jPanel2Layout.createSequentialGroup()
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k1k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k1k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k2k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k2k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k3k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k3k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k4k3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k4k2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                     .addGroup(jPanel2Layout.createSequentialGroup()
//                         .addComponent(k1k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addComponent(k2k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addComponent(k3k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addComponent(k4k4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                 .addContainerGap(25, Short.MAX_VALUE))
//         );

//         jLabel11.setText("Matriks Normalisasi");

//         jLabel12.setText("K1");

//         jLabel13.setText("K2");

//         jLabel14.setText("K3");

//         jLabel15.setText("K4");

//         k1k1N.setEditable(false);
//         k1k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k2N.setEditable(false);
//         k1k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k3N.setEditable(false);
//         k1k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k1k4N.setEditable(false);
//         k1k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k1N.setEditable(false);
//         k2k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k2N.setEditable(false);
//         k2k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k3N.setEditable(false);
//         k2k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k2k4N.setEditable(false);
//         k2k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k1N.setEditable(false);
//         k3k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k2N.setEditable(false);
//         k3k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k3N.setEditable(false);
//         k3k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k3k4N.setEditable(false);
//         k3k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k1N.setEditable(false);
//         k4k1N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k2N.setEditable(false);
//         k4k2N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k3N.setEditable(false);
//         k4k3N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         k4k4N.setEditable(false);
//         k4k4N.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         jLabel16.setText("K1");

//         jLabel17.setText("K2");

//         jLabel18.setText("K3");

//         jLabel19.setText("K4");

//         jLabel20.setText("Prioritas");

//         Prior1.setEditable(false);
//         Prior1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         Prior2.setEditable(false);
//         Prior2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         Prior3.setEditable(false);
//         Prior3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         Prior4.setEditable(false);
//         Prior4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

//         javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
//         jPanel3.setLayout(jPanel3Layout);
//         jPanel3Layout.setHorizontalGroup(
//             jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel3Layout.createSequentialGroup()
//                 .addGap(37, 37, 37)
//                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addComponent(jLabel11)
//                     .addGroup(jPanel3Layout.createSequentialGroup()
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                             .addGroup(jPanel3Layout.createSequentialGroup()
//                                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                     .addGroup(jPanel3Layout.createSequentialGroup()
//                                         .addComponent(jLabel13)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                         .addComponent(k2k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                         .addComponent(k2k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                     .addGroup(jPanel3Layout.createSequentialGroup()
//                                         .addComponent(jLabel14)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                         .addComponent(k3k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                         .addComponent(k3k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                     .addGroup(jPanel3Layout.createSequentialGroup()
//                                         .addComponent(jLabel15)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                         .addComponent(k4k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                         .addComponent(k4k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                     .addGroup(jPanel3Layout.createSequentialGroup()
//                                         .addComponent(jLabel12)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                         .addComponent(k1k1N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                         .addComponent(k1k2N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                                 .addGap(10, 10, 10)
//                                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                     .addComponent(k1k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k2k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k3k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k4k3N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                     .addComponent(k1k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k2k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k3k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                     .addComponent(k4k4N, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                             .addGroup(jPanel3Layout.createSequentialGroup()
//                                 .addGap(29, 29, 29)
//                                 .addComponent(jLabel16)
//                                 .addGap(40, 40, 40)
//                                 .addComponent(jLabel17)
//                                 .addGap(36, 36, 36)
//                                 .addComponent(jLabel18)
//                                 .addGap(40, 40, 40)
//                                 .addComponent(jLabel19)))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                             .addComponent(Prior4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel20)
//                             .addComponent(Prior3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
//                 .addContainerGap(43, Short.MAX_VALUE))
//         );
//         jPanel3Layout.setVerticalGroup(
//             jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel3Layout.createSequentialGroup()
//                 .addGap(12, 12, 12)
//                 .addComponent(jLabel11)
//                 .addGap(18, 18, 18)
//                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                     .addComponent(jLabel16)
//                     .addComponent(jLabel17)
//                     .addComponent(jLabel18)
//                     .addComponent(jLabel19)
//                     .addComponent(jLabel20))
//                 .addGap(5, 5, 5)
//                 .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addGroup(jPanel3Layout.createSequentialGroup()
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k1k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel12))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k2k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel13))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k3k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel14))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k4k1N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(jLabel15)))
//                     .addGroup(jPanel3Layout.createSequentialGroup()
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k1k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k1k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k2k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k2k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k3k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k3k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k4k3N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(k4k2N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                     .addGroup(jPanel3Layout.createSequentialGroup()
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k1k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k2k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k3k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                         .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                             .addComponent(k4k4N, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(Prior4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
//                 .addGap(13, 13, 13))
//         );

//         Simpan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
//         Simpan.setForeground(new java.awt.Color(179, 30, 144));
//         Simpan.setText("Simpan Data");
//         Simpan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 210, 226)));
//         Simpan.addMouseListener(new java.awt.event.MouseAdapter() {
//             public void mouseEntered(java.awt.event.MouseEvent evt) {
//                 SimpanMouseEntered(evt);
//             }
//             public void mouseExited(java.awt.event.MouseEvent evt) {
//                 SimpanMouseExited(evt);
//             }
//         });
//         Simpan.addActionListener(new java.awt.event.ActionListener() {
//             public void actionPerformed(java.awt.event.ActionEvent evt) {
//                 SimpanActionPerformed(evt);
//             }
//         });

//         jTable1.setModel(new javax.swing.table.DefaultTableModel(
//             new Object [][] {
//                 {null, null, null, null},
//                 {null, null, null, null},
//                 {null, null, null, null},
//                 {null, null, null, null}
//             },
//             new String [] {
//                 "Title 1", "Title 2", "Title 3", "Title 4"
//             }
//         ));
//         jScrollPane2.setViewportView(jTable1);

//         javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//         jPanel1.setLayout(jPanel1Layout);
//         jPanel1Layout.setHorizontalGroup(
//             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel1Layout.createSequentialGroup()
//                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
//                     .addComponent(jScrollPane2)
//                     .addGroup(jPanel1Layout.createSequentialGroup()
//                         .addContainerGap()
//                         .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                         .addGap(18, 18, 18)
//                         .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addGroup(jPanel1Layout.createSequentialGroup()
//                         .addGap(44, 44, 44)
//                         .addComponent(jSeparator1))
//                     .addGroup(jPanel1Layout.createSequentialGroup()
//                         .addGap(18, 18, 18)
//                         .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                             .addComponent(Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(mulaiHitung, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
//                         .addContainerGap(71, Short.MAX_VALUE))))
//         );

//         jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Simpan, mulaiHitung});

//         jPanel1Layout.setVerticalGroup(
//             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(jPanel1Layout.createSequentialGroup()
//                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                     .addGroup(jPanel1Layout.createSequentialGroup()
//                         .addContainerGap()
//                         .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                             .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                             .addComponent(mulaiHitung, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
//                 .addGap(18, 18, 18)
//                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                     .addGroup(jPanel1Layout.createSequentialGroup()
//                         .addComponent(Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
//                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                         .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
//                     .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
//                 .addContainerGap(561, Short.MAX_VALUE))
//         );

//         jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Simpan, mulaiHitung});

//         jScrollPane1.setViewportView(jPanel1);

//         javax.swing.GroupLayout PanelPerhitunganLayout = new javax.swing.GroupLayout(PanelPerhitungan);
//         PanelPerhitungan.setLayout(PanelPerhitunganLayout);
//         PanelPerhitunganLayout.setHorizontalGroup(
//             PanelPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addComponent(judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//             .addComponent(jScrollPane1)
//         );
//         PanelPerhitunganLayout.setVerticalGroup(
//             PanelPerhitunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addGroup(PanelPerhitunganLayout.createSequentialGroup()
//                 .addComponent(judul, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
//                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                 .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE))
//         );

//         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
//         setMinimumSize(new java.awt.Dimension(945, 600));
//         setSize(new java.awt.Dimension(945, 0));

//         Pane.setMinimumSize(new java.awt.Dimension(945, 525));
//         Pane.setPreferredSize(new java.awt.Dimension(945, 525));
//         Pane.setLayout(new java.awt.CardLayout());

//         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//         getContentPane().setLayout(layout);
//         layout.setHorizontalGroup(
//             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//         );
//         layout.setVerticalGroup(
//             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//             .addComponent(Pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//         );

//         setSize(new java.awt.Dimension(888, 577));
//         setLocationRelativeTo(null);
//     }// </editor-fold>//GEN-END:initComponents

//     //simpan data
//     private void SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SimpanActionPerformed
//         // TODO add your handling code here:
//         try{
//             SelectionModel newSelection = new SelectionModel();
//             newSelection.setUserId(Integer.parseInt(cbIdCalonPelamar.getSelectedItem().toString()));
//             newSelection.setScore(Double.parseDouble(textFieldTotalNilai.getText()));

//             selectionDao.upsertOne(newSelection);
//             JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
//             this.clearForm();
//         }catch (Exception e){
//             JOptionPane.showMessageDialog(null, "Data Gagal Disimpan "+e);
//         }
//     }//GEN-LAST:event_SimpanActionPerformed

//     private void SimpanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SimpanMouseExited
//         // TODO add your handling code here:
//         Simpan.setBackground(Color.white);
//     }//GEN-LAST:event_SimpanMouseExited

//     private void SimpanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SimpanMouseEntered
//         // TODO add your handling code here:
//         Simpan.setBackground(new Color(250, 239, 245));
//     }//GEN-LAST:event_SimpanMouseEntered
// //GEN-FIRST:event_mulaiHitungActionPerformed
//     //tombol mulai perhitungan
//     private void mulaiHitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mulaiHitungActionPerformed
//         try{
//             calculateComparisonCriteriaMatrix();
// //            String id = cbIdCalonPelamar.getSelectedItem().toString();
// //            double nilaiAlternatif;
// //            
// //            candidateFound =  candidateDao.findOneById(Integer.parseInt(id));
// //            if(candidateFound != null){
// //                namaCalonPelamar.setText(candidateFound.getName());
// //                System.out.println(ahpCalculation.getPriorityVector()[0]);
// //                System.out.println(ahpCalculation.getPriorityVector()[1]);
// //                System.out.println(ahpCalculation.getPriorityVector()[2]);
// //                System.out.println(ahpCalculation.getPriorityVector()[3]);
// //                nilaiAlternatif = (candidateFound.getLeadershipScore() * ahpCalculation.getPriorityVector()[0])
// //                        + ( candidateFound.getKnowledgeScore() * ahpCalculation.getPriorityVector()[1])
// //                        + (candidateFound.getTechnicalSkillScore() * ahpCalculation.getPriorityVector()[2])
// //                        + (candidateFound.getAdvancedSkillScore() * ahpCalculation.getPriorityVector()[3]);
// //                System.out.println(nilaiAlternatif);
// //                textFieldTotalNilai.setText(df2.format(nilaiAlternatif));
// //                
// //                double [][] pairwiseComparisonMatrix = ahpCalculation.getPairwiseComparisonMatrix();
// //                k1k1.setText(df2.format(pairwiseComparisonMatrix[0][0]));
// //                k1k2.setText(df2.format(pairwiseComparisonMatrix[0][1]));
// //                k1k3.setText(df2.format(pairwiseComparisonMatrix[0][2]));
// //                k1k4.setText(df2.format(pairwiseComparisonMatrix[0][3]));
// //                k2k1.setText(df.format(pairwiseComparisonMatrix[1][0]));
// //                k2k2.setText(df2.format(pairwiseComparisonMatrix[1][1]));
// //                k2k3.setText(df2.format(pairwiseComparisonMatrix[1][2]));
// //                k2k4.setText(df2.format(pairwiseComparisonMatrix[1][3]));
// //                k3k1.setText(df.format(pairwiseComparisonMatrix[2][0]));
// //                k3k2.setText(df.format(pairwiseComparisonMatrix[2][1]));
// //                k3k3.setText(df2.format(pairwiseComparisonMatrix[2][2]));
// //                k3k4.setText(df2.format(pairwiseComparisonMatrix[2][3]));
// //                k4k1.setText(df.format(pairwiseComparisonMatrix[3][0]));
// //                k4k2.setText(df.format(pairwiseComparisonMatrix[3][1]));
// //                k4k3.setText(df.format(pairwiseComparisonMatrix[3][2]));
// //                k4k4.setText(df2.format(pairwiseComparisonMatrix[3][3]));
// //                
// //                double [][] normalizedPairwiseComparisonMatrix = ahpCalculation.getNormalizedPairwiseComparisonMatrix();
// //                k1k1N.setText(df.format(normalizedPairwiseComparisonMatrix[0][0]));
// //                k1k2N.setText(df.format(normalizedPairwiseComparisonMatrix[0][1]));
// //                k1k3N.setText(df.format(normalizedPairwiseComparisonMatrix[0][2]));
// //                k1k4N.setText(df.format(normalizedPairwiseComparisonMatrix[0][3]));
// //                k2k1N.setText(df.format(normalizedPairwiseComparisonMatrix[1][0]));
// //                k2k2N.setText(df.format(normalizedPairwiseComparisonMatrix[1][1]));
// //                k2k3N.setText(df.format(normalizedPairwiseComparisonMatrix[1][2]));
// //                k2k4N.setText(df.format(normalizedPairwiseComparisonMatrix[1][3]));
// //                k3k1N.setText(df.format(normalizedPairwiseComparisonMatrix[2][0]));
// //                k3k2N.setText(df.format(normalizedPairwiseComparisonMatrix[2][1]));
// //                k3k3N.setText(df.format(normalizedPairwiseComparisonMatrix[2][2]));
// //                k3k4N.setText(df.format(normalizedPairwiseComparisonMatrix[2][3]));
// //                k4k1N.setText(df.format(normalizedPairwiseComparisonMatrix[3][0]));
// //                k4k2N.setText(df.format(normalizedPairwiseComparisonMatrix[3][1]));
// //                k4k3N.setText(df.format(normalizedPairwiseComparisonMatrix[3][2]));
// //                k4k4N.setText(df.format(normalizedPairwiseComparisonMatrix[3][3]));
// //                
// //                double [] priorityVector = ahpCalculation.getPriorityVector();
// //                Prior1.setText(df.format(priorityVector[0]));
// //                Prior2.setText(df.format(priorityVector[1]));
// //                Prior3.setText(df.format(priorityVector[2]));
// //                Prior4.setText(df.format(priorityVector[3]));
// //            }
//         }catch(Exception e){
//             JOptionPane.showMessageDialog(null,e);
//         }
//     }//GEN-LAST:event_mulaiHitungActionPerformed
// //GEN-LAST:event_mulaiHitungActionPerformed

//     private void mulaiHitungMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mulaiHitungMouseExited
//         // TODO add your handling code here:
//         mulaiHitung.setBackground(Color.white);
//     }//GEN-LAST:event_mulaiHitungMouseExited

//     private void mulaiHitungMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mulaiHitungMouseEntered
//         // TODO add your handling code here:
//         mulaiHitung.setBackground(new Color(250, 239, 245));
//     }//GEN-LAST:event_mulaiHitungMouseEntered

   
//     /**
//      * @param args the command line arguments
//      */
//     public static void main(String args[]) {
//         /* Set the Nimbus look and feel */
//         //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//         /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//          * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//          */
//         try {
//             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                 if ("Nimbus".equals(info.getName())) {
//                     javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                     break;
//                 }
//             }
//         } catch (ClassNotFoundException ex) {
//             java.util.logging.Logger.getLogger(AHPCalculationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (InstantiationException ex) {
//             java.util.logging.Logger.getLogger(AHPCalculationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (IllegalAccessException ex) {
//             java.util.logging.Logger.getLogger(AHPCalculationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//             java.util.logging.Logger.getLogger(AHPCalculationDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//         }
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>
//         //</editor-fold>

//         /* Create and display the dialog */
//         java.awt.EventQueue.invokeLater(new Runnable() {
//             public void run() {
//                 AHPCalculationDialog dialog = new AHPCalculationDialog(new javax.swing.JFrame(), true);
//                 dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                     @Override
//                     public void windowClosing(java.awt.event.WindowEvent e) {
//                         System.exit(0);
//                     }
//                 });
//                 dialog.setVisible(true);
//             }
//         });
//     }

//     // Variables declaration - do not modify//GEN-BEGIN:variables
//     private javax.swing.JPanel Pane;
//     private javax.swing.JPanel PanelPerhitungan;
//     private javax.swing.JTextField Prior1;
//     private javax.swing.JTextField Prior2;
//     private javax.swing.JTextField Prior3;
//     private javax.swing.JTextField Prior4;
//     private javax.swing.JButton Simpan;
//     private javax.swing.ButtonGroup btnG;
//     private javax.swing.JLabel jLabel10;
//     private javax.swing.JLabel jLabel11;
//     private javax.swing.JLabel jLabel12;
//     private javax.swing.JLabel jLabel13;
//     private javax.swing.JLabel jLabel14;
//     private javax.swing.JLabel jLabel15;
//     private javax.swing.JLabel jLabel16;
//     private javax.swing.JLabel jLabel17;
//     private javax.swing.JLabel jLabel18;
//     private javax.swing.JLabel jLabel19;
//     private javax.swing.JLabel jLabel2;
//     private javax.swing.JLabel jLabel20;
//     private javax.swing.JLabel jLabel3;
//     private javax.swing.JLabel jLabel4;
//     private javax.swing.JLabel jLabel5;
//     private javax.swing.JLabel jLabel6;
//     private javax.swing.JLabel jLabel7;
//     private javax.swing.JLabel jLabel8;
//     private javax.swing.JLabel jLabel9;
//     private javax.swing.JPanel jPanel1;
//     private javax.swing.JPanel jPanel2;
//     private javax.swing.JPanel jPanel3;
//     private javax.swing.JScrollPane jScrollPane1;
//     private javax.swing.JScrollPane jScrollPane2;
//     private javax.swing.JSeparator jSeparator1;
//     private javax.swing.JTable jTable1;
//     private javax.swing.JLabel judul;
//     private javax.swing.JTextField k1k1;
//     private javax.swing.JTextField k1k1N;
//     private javax.swing.JTextField k1k2;
//     private javax.swing.JTextField k1k2N;
//     private javax.swing.JTextField k1k3;
//     private javax.swing.JTextField k1k3N;
//     private javax.swing.JTextField k1k4;
//     private javax.swing.JTextField k1k4N;
//     private javax.swing.JTextField k2k1;
//     private javax.swing.JTextField k2k1N;
//     private javax.swing.JTextField k2k2;
//     private javax.swing.JTextField k2k2N;
//     private javax.swing.JTextField k2k3;
//     private javax.swing.JTextField k2k3N;
//     private javax.swing.JTextField k2k4;
//     private javax.swing.JTextField k2k4N;
//     private javax.swing.JTextField k3k1;
//     private javax.swing.JTextField k3k1N;
//     private javax.swing.JTextField k3k2;
//     private javax.swing.JTextField k3k2N;
//     private javax.swing.JTextField k3k3;
//     private javax.swing.JTextField k3k3N;
//     private javax.swing.JTextField k3k4;
//     private javax.swing.JTextField k3k4N;
//     private javax.swing.JTextField k4k1;
//     private javax.swing.JTextField k4k1N;
//     private javax.swing.JTextField k4k2;
//     private javax.swing.JTextField k4k2N;
//     private javax.swing.JTextField k4k3;
//     private javax.swing.JTextField k4k3N;
//     private javax.swing.JTextField k4k4;
//     private javax.swing.JTextField k4k4N;
//     private javax.swing.JButton mulaiHitung;
//     // End of variables declaration//GEN-END:variables

//     void show(JRootPane rootPane) {
//         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//     }
// }
