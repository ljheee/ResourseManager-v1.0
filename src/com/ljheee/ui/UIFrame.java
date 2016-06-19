package com.ljheee.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;



/**
 * Resourse Manager--��Դ������
 * @author ljheee
 * @param <JScroPanel>
 *
 */
public class UIFrame<JScroPanel> extends JFrame {
	
	private JButton leftButton ,rightButton,switchTo,findButton;
	private JComboBox< String> add_url; //��ַ��--����JComboBox
	private JTextField find_line;//������
	
	JTree jtree;
	DefaultMutableTreeNode parent;
	FileSystemView fsv = FileSystemView.getFileSystemView();//���Ի��ϵͳͼ��
	DefaultMutableTreeNode treeRoot;//��Ŀ¼--������
	
	private JLabel fileInfo = new JLabel("״̬��:");
	private JLabel pathInfo = new JLabel("  ");
	private JLabel timeInfo = new JLabel("  ");
	
	DefaultListModel listModel = null;
	 
	public UIFrame() throws UnknownHostException{
		this.setTitle("Resourse Manager");
		this.setSize(870,632);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		JPanel topJPanel = new JPanel();
		topJPanel.setLayout(new GridLayout(2, 1));
		JPanel jpanel1 = new JPanel();
		jpanel1.setBackground(Color.pink);
		topJPanel.setSize(this.getWidth(), 30);
		leftButton = new JButton( new ImageIcon("images/left.png"));
		leftButton.setMargin(new Insets(0, 0, 0, 0));
		leftButton.setToolTipText("����");
		rightButton = new JButton(new ImageIcon("images/right.png"));
		rightButton.setMargin(new Insets(0, 0, 0, 0));
		rightButton.setToolTipText("ǰ��");
//		add_url = new JTextField(40);
		add_url = new JComboBox<String>();
		add_url.setEditable(true);
//		add_url.setSize(420, 25);
		add_url.setPreferredSize(new Dimension(this.getWidth()-425, 25));
		switchTo = new JButton( new ImageIcon("images/switchTo.png"));
		switchTo.setMargin(new Insets(0, 0, 0, 0));
		switchTo.setToolTipText("��ת��");
		find_line = new JTextField("���� icons",20);
		findButton = new JButton(new ImageIcon("images/find.png"));
		findButton.setMargin(new Insets(0, 0, 0, 0));
		findButton.setToolTipText("����");
		jpanel1.add(leftButton);
		jpanel1.add(rightButton);
		jpanel1.add(add_url);
		jpanel1.add(switchTo);
		jpanel1.add(find_line);
		jpanel1.add(findButton);
		
		
		JRootPane rootPane = new JRootPane(); //��panel����Ӳ˵�
		rootPane.setBackground(Color.gray);
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("�ļ�"); 
		JMenu editMenu = new JMenu("�༭"); 
		JMenu viewMenu = new JMenu("�鿴"); 
		JMenu toolMenu = new JMenu("����"); 
		JMenu helpMenu = new JMenu("����"); 
		
		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
		
		//���˵�  ��Ӳ˵���
		fileMenu.add(new JMenuItem("��"));
		fileMenu.add(new JMenuItem("����"));
		fileMenu.add(new JMenuItem("��ӡ"));
		
		topJPanel.add(jpanel1);//����ͼ��panel: ǰ�������ˡ���ַ����������
		topJPanel.add(rootPane);//����panel :�ļ����༭���鿴
		this.add(topJPanel,BorderLayout.NORTH);
		
		
		
		//״̬��---ʹ��JToolBar����Ϊ�����϶�
		JToolBar  bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);//����JToolBar�����϶�
				
		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 20));
		bottomToolBar.add(fileInfo);
				
//		bottomToolBar.addSeparator(); //�˷�����ӷָ���  ��Ч
		JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);//��ӷָ���
				
		fileInfo.setPreferredSize(new Dimension(200, 20));
		fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				
		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//��ӷָ���
				
		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 20));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));
				
		this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);//����--�š�״̬����
		
		
		//�����
		InetAddress local = InetAddress.getLocalHost();
		treeRoot = new DefaultMutableTreeNode(local.getHostName());
		jtree = new JTree(treeRoot);
		jtree.addTreeSelectionListener(new MyTreeSelectionListener());
		JScrollPane jScrollPane = new JScrollPane(jtree);
		jScrollPane.setPreferredSize(new Dimension(200, 525));
//		this.getContentPane().add(jScrollPane, BorderLayout.WEST);//���--�š��������
				
		
		JList fileList = new JList();//�м���ʾ--����JList
		fileList.setFixedCellHeight(25);//���� ÿ��--�߶�
		listModel = new DefaultListModel();
		
		fileList.setModel(listModel);

//		JPanel centerPanel = new JPanel();//�м����
//		centerPanel.setBackground(Color.BLACK);
//		this.add(centerPanel); //Ĭ�Ϸ�--center�м�
//		centerPanel.setDoubleBuffered(true);
		
		jScrollPane.setDoubleBuffered(true);
		fileList.setDoubleBuffered(true);
		JScrollPane filesScrollPane  = new JScrollPane(fileList);
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, filesScrollPane);
		jSplitPane.setDividerSize(4);
		this.add(jSplitPane);//jSplitPane�����м䣬����������panel--�����϶����루�����˷ֱ����BorderLayout.WEST��center��
		
		
		//��ӵ��Ա���Ӳ��--����Ŀ¼
		for (char i = 'c'; i < 'z'; i++) {
		   String path = i + ":/";
		   if (((new File(path)).exists())) {
		    DefaultMutableTreeNode diskNode = new DefaultMutableTreeNode(new File(path));
		    treeRoot.add(diskNode);
		    readfiles(new File(path), diskNode);
		   }
		}		

		
		
		this.setVisible(true);
	}
	
	/**
	 * ��ȡ��ѡ�ڵ�,��ȡ�ӽڵ�
	 * @param file
	 * @param node ��ǰѡ�нڵ㣨����������ӽڵ㣬���Զ���ӣ�
	 */
	 public void readfiles(File file, DefaultMutableTreeNode node) {

		  File list[] = file.listFiles();
		  if (list == null)  return;
		   
		 for (int i = 0; i < list.length; i++) {
		   File file_inlist = list[i];
		   // String filename = file_inlist.getName();
		 
		   if (file_inlist.isDirectory()) {
			   parent = new DefaultMutableTreeNode(file_inlist);
			   // ��ӿհ��ļ��нڵ� ʹ�ӽڵ���ʾΪ�ļ���
			   File stubadd = null;
			   DefaultMutableTreeNode stub = new DefaultMutableTreeNode(stubadd);
			   parent.add(stub);
			   node.add(parent);
//			   readfiles(file_inlist, parent);//�˴��ݹ飬�������ļ����µĽڵ�  ���
			   
		   }else{   //�����ļ�--ֱ����ӽڵ�
			   DefaultMutableTreeNode son = new DefaultMutableTreeNode(file_inlist);
			   node.add(son);
		   }
		 }

	 }
	
	
	/**
	 * JTree--ѡ��ڵ����
	 * @author ljheee
	 */
	class MyTreeSelectionListener implements TreeSelectionListener {

	//	@Override
		public void valueChanged(TreeSelectionEvent e) {
			listModel.removeAllElements();
			
			 TreePath path = jtree.getSelectionPath();
			 if (path == null) return;   //�ȼ���==!tree.isSelectionEmpty()
//			 System.out.println(e.getPath());
			
			 
			 jtree.expandPath(e.getPath());//չ���ڵ�
			 DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
			 add_url.setSelectedItem(selectnode.getUserObject().toString());//���õ�ַ��
			 if (!selectnode.isLeaf()) {
				     // ������������ж�
				 if (!(selectnode.getUserObject() instanceof File)) {
				      return;
				 }
				   
				 File file_select = (File) selectnode.getUserObject();
			     // ��ȡ�ļ������ļ�����²�ڵ�
			     readfiles(file_select, selectnode);
			     DefaultMutableTreeNode firstchild = (DefaultMutableTreeNode) selectnode
			       .getFirstChild();
			     selectnode.remove(firstchild);
			     
			     File[] fileList = file_select.listFiles();
			     if(fileList!=null){
			    	 for(int i = 0; i < fileList.length; i++) {
					    	listModel.addElement("    "+fileList[i].getName());
					    	
					 }
			     }
			     
			 }
		}
		
		
	
	}
	
	
	
	
	public static void main(String[] args) throws UnknownHostException {
		new UIFrame();
	}
}
