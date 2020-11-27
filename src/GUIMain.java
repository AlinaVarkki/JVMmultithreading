import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.*;

public class GUIMain {

    public static void main(String[] args) {

        GUI gui = new GUI();
    }

}

class GUI{

    DefaultMutableTreeNode threadHierarchy;
    ThreadInfoPrinter threadInfoPrinter;
    DefaultTreeModel tree;
    Timer t;
    Timer labelUpdate;
    long tslu;

    public GUI(){
        //Setting up GUI, enter at own risk :)!
        tslu = System.currentTimeMillis();
        threadInfoPrinter = new ThreadInfoPrinter();
        //threadHierarchy = new DefaultMutableTreeNode();
        threadHierarchy = updateHierarchy();
        tree = new DefaultTreeModel(threadHierarchy);



        {
            ImageIcon leafIcon = new ImageIcon("src/Thread.png");
            ImageIcon treeIcon = new ImageIcon("src/ThreadGroups.png");
            Border loweredbevel = BorderFactory.createLoweredBevelBorder();
            JFrame frame = new JFrame();
            frame.setIconImage(treeIcon.getImage());
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            GridBagConstraints mainGBC = new GridBagConstraints();
            mainGBC.fill = GridBagConstraints.BOTH;
            mainGBC.weighty = 0.5;


            JPanel listPanel = new JPanel();
            listPanel.setLayout(new GridBagLayout());
            GridBagConstraints listGBC = new GridBagConstraints();
            listGBC.insets = new Insets(3, 3, 3, 3);
            listGBC.ipadx = 3;
            listGBC.ipady = 3;

            JTextField searchBar = new JTextField();
            listGBC.gridx = 0;
            listGBC.gridy = 0;
            listGBC.gridwidth = 3;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.67;
            listGBC.weighty = 0.2;
            listGBC.fill = GridBagConstraints.HORIZONTAL;
            listPanel.add(searchBar, listGBC);

            JButton searchButton = new JButton();
            searchButton.setText("Search");
            listGBC.gridx = 3;
            listGBC.gridy = 0;
            listGBC.gridwidth = 1;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.33;
            listGBC.weighty = 0.2;
            listGBC.fill = GridBagConstraints.NONE;
            listPanel.add(searchButton, listGBC);

            JTree threadDisplay = new JTree(tree);
            if (leafIcon != null && treeIcon != null) {
                DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
                renderer.setLeafIcon(leafIcon);
                renderer.setOpenIcon(treeIcon);
                renderer.setClosedIcon(treeIcon);
                threadDisplay.setCellRenderer(renderer);
            }
            threadDisplay.setVisibleRowCount(10);
            JScrollPane threadDisplayScroll = new JScrollPane(threadDisplay);
            listGBC.gridx = 0;
            listGBC.gridy = 1;
            listGBC.gridwidth = 4;
            listGBC.gridheight = 1;
            listGBC.weightx = 1;
            listGBC.weighty = 0.4;
            listGBC.fill = GridBagConstraints.BOTH;
            listPanel.add(threadDisplayScroll, listGBC);

            JLabel autoUpdateLabel = new JLabel();
            autoUpdateLabel.setText("Auto Update: ");
            listGBC.gridx = 0;
            listGBC.gridy = 2;
            listGBC.gridwidth = 1;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.2;
            listGBC.weighty = 0.2;
            listGBC.fill = GridBagConstraints.NONE;
            listPanel.add(autoUpdateLabel, listGBC);

            JCheckBox autoUpdate = new JCheckBox();

            listGBC.gridx = 1;
            listGBC.gridy = 2;
            listGBC.gridwidth = 1;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.05;
            listGBC.weighty = 0.2;
            listPanel.add(autoUpdate, listGBC);

            JButton updateDisplay = new JButton();
            updateDisplay.setText("Update");
            listGBC.gridx = 2;
            listGBC.gridy = 2;
            listGBC.gridwidth = 1;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.2;
            listGBC.weighty = 0.2;
            listPanel.add(updateDisplay, listGBC);

            JLabel TSLULabel = new JLabel();
            TSLULabel.setFont(new Font("Calibri", Font.PLAIN, 11));
            TSLULabel.setText("TSLU: xms");
            listGBC.gridx = 3;
            listGBC.gridy = 2;
            listGBC.gridwidth = 1;
            listGBC.gridheight = 1;
            listGBC.weightx = 0.5;
            listGBC.weighty = 0.2;
            listPanel.add(TSLULabel, listGBC);

            JSlider autoUpdateFrequency = new JSlider(0, 5000, 2000);
            autoUpdateFrequency.setMajorTickSpacing(1000);
            autoUpdateFrequency.setMinorTickSpacing(100);
            autoUpdateFrequency.setPaintTicks(true);
            autoUpdateFrequency.setPaintLabels(true);
            listGBC.insets = new Insets(3, 3, 3, 3);
            listGBC.gridx = 0;
            listGBC.gridy = 3;
            listGBC.gridwidth = 4;
            listGBC.gridheight = 1;
            listGBC.weightx = 1;
            listGBC.weighty = 0.2;
            listGBC.fill = GridBagConstraints.HORIZONTAL;
            listPanel.add(autoUpdateFrequency, listGBC);

            listPanel.setBorder(loweredbevel);
            mainGBC.gridx = 0;
            mainGBC.gridy = 0;
            mainGBC.gridheight = 2;
            mainGBC.weightx = 0.667;
            mainGBC.insets = new Insets(3, 3, 3, 3);
            panel.add(listPanel, mainGBC);


            JPanel createPanel = new JPanel();
            createPanel.setLayout(new GridBagLayout());
            GridBagConstraints createGBC = new GridBagConstraints();
            createGBC.insets = new Insets(3, 3, 3, 3);
            createGBC.ipadx = 3;
            createGBC.ipady = 3;

            JLabel threadNameLabel = new JLabel();
            threadNameLabel.setText("Thread Name: ");
            createGBC.gridx = 0;
            createGBC.gridy = 0;
            createGBC.gridwidth = 1;
            createGBC.gridheight = 1;
            createGBC.weightx = 0.5;
            createGBC.weighty = 0.33;
            createGBC.fill = GridBagConstraints.HORIZONTAL;
            createPanel.add(threadNameLabel, createGBC);

            JTextField threadNameTextBox = new JTextField();
            createGBC.gridx = 1;
            createGBC.gridy = 0;
            createGBC.fill = GridBagConstraints.HORIZONTAL;
            createPanel.add(threadNameTextBox, createGBC);

            JButton createThreadButton = new JButton();
            createThreadButton.setText("Create Thread");
            createThreadButton.setActionCommand("createThread");
            createGBC.gridx = 1;
            createGBC.gridy = 2;
            createGBC.fill = GridBagConstraints.NONE;
            createPanel.add(createThreadButton, createGBC);

            createPanel.setBorder(loweredbevel);
            mainGBC.gridx = 1;
            mainGBC.gridy = 0;
            mainGBC.gridheight = 1;
            mainGBC.weightx = 0.333;
            mainGBC.insets = new Insets(3, 0, 3, 3);
            panel.add(createPanel, mainGBC);


            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridBagLayout());
            GridBagConstraints infoGBC = new GridBagConstraints();
            infoGBC.fill = GridBagConstraints.BOTH;
            infoGBC.weightx = 0.5;
            infoGBC.weighty = 0.5;

            String[][] empty = {{"Thread Name: ", ""}, {"Thread ID: ", ""}, {"Thread Stage: ", ""}, {"Thread Priority: ", ""}, {"Daemon?: ", ""}, {"Thread Group: ", ""}};
            String[] temp = {"", ""};
            JTable infoTable = new JTable(empty, temp);
            infoTable.setTableHeader(null);
            infoPanel.add(infoTable, infoGBC);

            infoPanel.setBorder(loweredbevel);
            mainGBC.gridx = 1;
            mainGBC.gridy = 1;
            mainGBC.gridheight = 1;
            mainGBC.insets = new Insets(0, 0, 3, 3);
            panel.add(infoPanel, mainGBC);

            updateDisplay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    threadHierarchy = updateHierarchy();
                    tree.setRoot(threadHierarchy);
                    tree.nodeChanged(threadHierarchy);
                    tree.reload(threadHierarchy);
                    tslu = System.currentTimeMillis();
                    System.out.println("updating");
                }
            });
            createThreadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!threadNameTextBox.getText().equals("")) {
                        startNewThread(threadNameTextBox.getText());
                    }
                }
            });

            autoUpdate.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(autoUpdate.isSelected()){
                        System.out.println("now shifting into maximum overdrive");
                        if(autoUpdateFrequency.getValue() != 0) {
                            t.start();
                        }
                        else{
                            autoUpdate.setSelected(false);
                        }
                    }
                    else{
                        System.out.println("taking a break");
                        t.stop();
                    }
                }
            });

            autoUpdateFrequency.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if(autoUpdateFrequency.getValue() == 0){
                        autoUpdate.setSelected(false);
                        autoUpdate.setEnabled(false);
                    }
                    else{
                        autoUpdate.setEnabled(true);
                        t.setDelay(autoUpdateFrequency.getValue());
                    }
                }
            });

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!searchBar.getText().equals("")){
                        threadHierarchy = updateHierarchy(searchBar.getText());
                        tree.setRoot(threadHierarchy);
                        tree.nodeChanged(threadHierarchy);
                        tree.reload(threadHierarchy);
                        tslu = System.currentTimeMillis();
                        System.out.println("updating");
                    }
                    else{
                        threadHierarchy = updateHierarchy();
                        tree.setRoot(threadHierarchy);
                        tree.nodeChanged(threadHierarchy);
                        tree.reload(threadHierarchy);
                        tslu = System.currentTimeMillis();
                        System.out.println("updating");
                    }
                }
            });

            t = new Timer(autoUpdateFrequency.getValue(), new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    threadHierarchy = updateHierarchy();
                    tree.setRoot(threadHierarchy);
                    tree.nodeChanged(threadHierarchy);
                    tree.reload(threadHierarchy);
                    tslu = System.currentTimeMillis();
                    System.out.println("updating");
                }
            });

            labelUpdate = new Timer(5, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long temp = System.currentTimeMillis() - tslu;
                    TSLULabel.setText("TSLU: " + temp + "ms");
                }
            });

            threadDisplay.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)threadDisplay.getLastSelectedPathComponent();
                    if (selectedNode != null) {

                        if (selectedNode.isLeaf()) {
                            for (Thread thread : threadInfoPrinter.getAllThreads(threadInfoPrinter.getRootThreadGroup())) {
                                if (selectedNode.getUserObject().equals(thread.getId() + " - " + thread.getName())) {
                                    infoTable.getModel().setValueAt(thread.getName(), 0, 1);
                                    infoTable.getModel().setValueAt(String.valueOf(thread.getId()), 1, 1);
                                    infoTable.getModel().setValueAt(thread.getState().toString(), 2, 1);
                                    infoTable.getModel().setValueAt(String.valueOf(thread.getPriority()), 3, 1);

                                    if (thread.isDaemon()) {
                                        infoTable.getModel().setValueAt("Yes", 4, 1);
                                    } else {
                                        infoTable.getModel().setValueAt("No", 4, 1);
                                    }
                                    infoTable.getModel().setValueAt(thread.getThreadGroup().getName(), 5, 1);
                                }
                            }


                        }
                    }
                }
            });
            labelUpdate.start();
            t.stop();
            frame.add(panel, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Thread Analyser");
            frame.pack();
            frame.setVisible(true);
        }
    }
    public DefaultMutableTreeNode updateHierarchy(){
        DefaultMutableTreeNode hierarchy = new DefaultMutableTreeNode(threadInfoPrinter.getRootThreadGroup().getName());
        Thread[] threads = threadInfoPrinter.getAllThreads(threadInfoPrinter.getRootThreadGroup());
        ThreadGroup[] threadGroups = threadInfoPrinter.getAllThreadGroups();

        addThreadsToTree(threads, threadGroups, threadInfoPrinter.getRootThreadGroup(), hierarchy);

        return hierarchy;
    }

    public DefaultMutableTreeNode updateHierarchy(String query){
        DefaultMutableTreeNode hierarchy = new DefaultMutableTreeNode(threadInfoPrinter.getRootThreadGroup().getName());
        Thread[] threads = threadInfoPrinter.getAllThreads(threadInfoPrinter.getRootThreadGroup());
        ThreadGroup[] threadGroups = threadInfoPrinter.getAllThreadGroups();

        addThreadsToTree(threads, threadGroups, threadInfoPrinter.getRootThreadGroup(), hierarchy,query);

        return hierarchy;
    }

    public DefaultMutableTreeNode addThreadsToTree(Thread[] threads, ThreadGroup[] threadGroups, ThreadGroup rootGroup, DefaultMutableTreeNode root){
        for (Thread thread: threads){
            if(thread.getThreadGroup() == rootGroup){
                root.insert(new DefaultMutableTreeNode(thread.getId() + " - " + thread.getName()), root.getChildCount());
            }
        }
        for(ThreadGroup threadGroup: threadGroups){
            if(threadGroup.getParent() == rootGroup){
                root.insert(addThreadsToTree(threads,threadGroups,threadGroup,new DefaultMutableTreeNode(threadGroup.getName())),root.getChildCount());
            }
        }

        return root;
    }

    public DefaultMutableTreeNode addThreadsToTree(Thread[] threads, ThreadGroup[] threadGroups, ThreadGroup rootGroup, DefaultMutableTreeNode root, String query){
        for (Thread thread: threads){
            if(thread.getThreadGroup() == rootGroup && thread.getName().contains(query)){
                root.insert(new DefaultMutableTreeNode(thread.getId() + " - " + thread.getName()), root.getChildCount());
            }
        }
        for(ThreadGroup threadGroup: threadGroups){
            if(threadGroup.getParent() == rootGroup){
                if(threadGroup.getName().contains(query)) {
                    root.insert(addThreadsToTree(threads, threadGroups, threadGroup, new DefaultMutableTreeNode(threadGroup.getName()),query), root.getChildCount());
                }
                else{
                    for (Thread thread: threads){
                        if(thread.getThreadGroup() == threadGroup && thread.getName().contains(query)){
                            root.insert(addThreadsToTree(threads, threadGroups, threadGroup, new DefaultMutableTreeNode(threadGroup.getName()),query), root.getChildCount());
                            break;
                        }

                    }
                }
            }
        }

        return root;
    }

    public void startNewThread(String name){
        threadInfoPrinter.startNewThread(name);
    }

    public void startTimer(){
        t.start();
    }

    public void stopTimer(){
        t.stop();
    }

}
