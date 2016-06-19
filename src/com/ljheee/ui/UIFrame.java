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
 * Resourse Manager--资源管理器
 * @author ljheee
 * @param <JScroPanel>
 *
 */
public class UIFrame<JScroPanel> extends JFrame {
	
	private JButton leftButton ,rightButton,switchTo,findButton;
	private JComboBox< String> add_url; //地址栏--改用JComboBox
	private JTextField find_line;//搜索栏
	
	JTree jtree;
	DefaultMutableTreeNode parent;
	FileSystemView fsv = FileSystemView.getFileSystemView();//可以获得系统图标
	DefaultMutableTreeNode treeRoot;//根目录--本机名
	
	private JLabel fileInfo = new JLabel("状态栏:");
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
		leftButton.setToolTipText("返回");
		rightButton = new JButton(new ImageIcon("images/right.png"));
		rightButton.setMargin(new Insets(0, 0, 0, 0));
		rightButton.setToolTipText("前进");
//		add_url = new JTextField(40);
		add_url = new JComboBox<String>();
		add_url.setEditable(true);
//		add_url.setSize(420, 25);
		add_url.setPreferredSize(new Dimension(this.getWidth()-425, 25));
		switchTo = new JButton( new ImageIcon("images/switchTo.png"));
		switchTo.setMargin(new Insets(0, 0, 0, 0));
		switchTo.setToolTipText("跳转到");
		find_line = new JTextField("搜索 icons",20);
		findButton = new JButton(new ImageIcon("images/find.png"));
		findButton.setMargin(new Insets(0, 0, 0, 0));
		findButton.setToolTipText("查找");
		jpanel1.add(leftButton);
		jpanel1.add(rightButton);
		jpanel1.add(add_url);
		jpanel1.add(switchTo);
		jpanel1.add(find_line);
		jpanel1.add(findButton);
		
		
		JRootPane rootPane = new JRootPane(); //此panel，添加菜单
		rootPane.setBackground(Color.gray);
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("文件"); 
		JMenu editMenu = new JMenu("编辑"); 
		JMenu viewMenu = new JMenu("查看"); 
		JMenu toolMenu = new JMenu("工具"); 
		JMenu helpMenu = new JMenu("帮助"); 
		
		rootPane.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
		
		//给菜单  添加菜单项
		fileMenu.add(new JMenuItem("打开"));
		fileMenu.add(new JMenuItem("保存"));
		fileMenu.add(new JMenuItem("打印"));
		
		topJPanel.add(jpanel1);//导航图标panel: 前进、后退、地址栏、搜索栏
		topJPanel.add(rootPane);//工具panel :文件、编辑、查看
		this.add(topJPanel,BorderLayout.NORTH);
		
		
		
		//状态栏---使用JToolBar，设为不可拖动
		JToolBar  bottomToolBar = new JToolBar();
		bottomToolBar.setFloatable(false);//设置JToolBar不可拖动
				
		bottomToolBar.setPreferredSize(new Dimension(this.getWidth(), 20));
		bottomToolBar.add(fileInfo);
				
//		bottomToolBar.addSeparator(); //此方法添加分隔符  无效
		JSeparator  jsSeparator = new JSeparator(SwingConstants.VERTICAL);
		bottomToolBar.add(jsSeparator);//添加分隔符
				
		fileInfo.setPreferredSize(new Dimension(200, 20));
		fileInfo.setHorizontalTextPosition(SwingConstants.LEFT);
				
		bottomToolBar.add(pathInfo);
		pathInfo.setHorizontalTextPosition(SwingConstants.LEFT);
		bottomToolBar.add(new JSeparator(SwingConstants.VERTICAL));//添加分隔符
				
		bottomToolBar.add(timeInfo);
		timeInfo.setPreferredSize(new Dimension(70, 20));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		timeInfo.setText(sdf.format(new Date()));
				
		this.getContentPane().add(bottomToolBar, BorderLayout.SOUTH);//下面--放“状态栏”
		
		
		//树组件
		InetAddress local = InetAddress.getLocalHost();
		treeRoot = new DefaultMutableTreeNode(local.getHostName());
		jtree = new JTree(treeRoot);
		jtree.addTreeSelectionListener(new MyTreeSelectionListener());
		JScrollPane jScrollPane = new JScrollPane(jtree);
		jScrollPane.setPreferredSize(new Dimension(200, 525));
//		this.getContentPane().add(jScrollPane, BorderLayout.WEST);//左侧--放“树组件”
				
		
		JList fileList = new JList();//中间显示--换用JList
		fileList.setFixedCellHeight(25);//设置 每格--高度
		listModel = new DefaultListModel();
		
		fileList.setModel(listModel);

//		JPanel centerPanel = new JPanel();//中间面板
//		centerPanel.setBackground(Color.BLACK);
//		this.add(centerPanel); //默认放--center中间
//		centerPanel.setDoubleBuffered(true);
		
		jScrollPane.setDoubleBuffered(true);
		fileList.setDoubleBuffered(true);
		JScrollPane filesScrollPane  = new JScrollPane(fileList);
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, filesScrollPane);
		jSplitPane.setDividerSize(4);
		this.add(jSplitPane);//jSplitPane放在中间，它包含左右panel--可以拖动分离（代替了分别放在BorderLayout.WEST和center）
		
		
		//添加电脑本地硬盘--到根目录
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
	 * 读取所选节点,获取子节点
	 * @param file
	 * @param node 当前选中节点（如果其下游子节点，则自动添加）
	 */
	 public void readfiles(File file, DefaultMutableTreeNode node) {

		  File list[] = file.listFiles();
		  if (list == null)  return;
		   
		 for (int i = 0; i < list.length; i++) {
		   File file_inlist = list[i];
		   // String filename = file_inlist.getName();
		 
		   if (file_inlist.isDirectory()) {
			   parent = new DefaultMutableTreeNode(file_inlist);
			   // 添加空白文件夹节点 使子节点显示为文件夹
			   File stubadd = null;
			   DefaultMutableTreeNode stub = new DefaultMutableTreeNode(stubadd);
			   parent.add(stub);
			   node.add(parent);
//			   readfiles(file_inlist, parent);//此处递归，给所有文件夹下的节点  添加
			   
		   }else{   //单个文件--直接添加节点
			   DefaultMutableTreeNode son = new DefaultMutableTreeNode(file_inlist);
			   node.add(son);
		   }
		 }

	 }
	
	
	/**
	 * JTree--选择节点监听
	 * @author ljheee
	 */
	class MyTreeSelectionListener implements TreeSelectionListener {

	//	@Override
		public void valueChanged(TreeSelectionEvent e) {
			listModel.removeAllElements();
			
			 TreePath path = jtree.getSelectionPath();
			 if (path == null) return;   //等价于==!tree.isSelectionEmpty()
//			 System.out.println(e.getPath());
			
			 
			 jtree.expandPath(e.getPath());//展开节点
			 DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path.getLastPathComponent();
			 add_url.setSelectedItem(selectnode.getUserObject().toString());//设置地址栏
			 if (!selectnode.isLeaf()) {
				     // 这里加上类型判断
				 if (!(selectnode.getUserObject() instanceof File)) {
				      return;
				 }
				   
				 File file_select = (File) selectnode.getUserObject();
			     // 读取文件夹下文件添加下层节点
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
