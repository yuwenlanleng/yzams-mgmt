/*
 * AttendanceMgmtPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 10:36:17
 */
package com.yz.ams.client.ui.attendence;

import com.nazca.sql.PageResult;
import com.nazca.ui.NWaitingPanel;
import com.nazca.ui.TextHinter;
import com.nazca.ui.UIUtilities;
import com.nazca.ui.laf.border.IconLabelBorder;
import com.nazca.ui.pagination.PaginationListener;
import com.nazca.ui.pagination.TablePageSession;
import com.nazca.ui.util.CardLayoutWrapper;
import com.nazca.usm.model.USMSUser;
import com.yz.ams.client.ClientContext;
import com.yz.ams.client.DeleteOperationPanel;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.agent.DeleteAttendenceAgent;
import com.yz.ams.client.agent.QueryAttendanceAgent;
import com.yz.ams.client.model.AttendanceTableMode;
import com.yz.ams.client.renderer.AttendanceTableRenderer;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.model.Attendance;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class AttendanceMgmtPanel extends javax.swing.JPanel {

    private CardLayoutWrapper leftCard = null;
    private IconLabelBorder leftBorder = null;
    private AttendanceTableMode tableModel = null;
    private AttendanceTableRenderer tableRenderer = null;
    private QueryAttendanceAgent queryAttendanceAgent = null;
    private Attendance curAttendance = null;
    private DeleteAttendenceAgent deleteAgent = null;

    /**
     * Creates new form AttendanceMgmtPanel
     */
    public AttendanceMgmtPanel() {
        initComponents();
        initCommon();
        initModelAndRenderer();
        initUI();
        initAgentAndListener();
        init();
    }

    private void initCommon() {
        leftCard = new CardLayoutWrapper(cardPane1);
    }

    private void initModelAndRenderer() {
        tableModel = new AttendanceTableMode();
        tableComp.setModel(tableModel);
        tableRenderer = new AttendanceTableRenderer();
        tableComp.setDefaultRenderer(Object.class, tableRenderer);
        TableRowSorter<TableModel> rightRowSorter = UIUtilities.generateAndSetTriStateRowSorter(tableComp, tableModel);
        tableComp.setRowSorter(rightRowSorter);
    }

    private void initUI() {
        UIUtilities.attachSearchIcon(searchTxFd);//给搜索框加搜索图标
        TextHinter.attach("输入内容并回车", searchTxFd);
    }

    private void initAgentAndListener() {
        queryAttendanceAgent = new QueryAttendanceAgent();
        queryAttendanceAgent.addListener(queryAttendanceAgentLis);
        pagePane.addPaginationListener(new PaginationListener() {//数据监听
            @Override
            public void onPageChanged(TablePageSession page) {
                refreshBtnActionPerformed(null);
            }
        });
        tableComp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {//分页监听
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectingVacation();
                }
            }
        });
        deleteAgent = new DeleteAttendenceAgent();
        searchTxFd.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ENTER == e.getKeyCode()) {
                    refreshBtnActionPerformed(null);
                }
            }
        });
    }

    private void selectingVacation() {//分页数据监听
        int idx = tableComp.getSelectedRow();
        if (idx >= 0) {
            idx = tableComp.convertRowIndexToModel(idx);
            curAttendance = tableModel.getData(idx);
            updateBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        } else {
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }
    }

    private void setCompsEnabled(boolean enabled) {//刷新时禁用所有按钮
        refreshBtn.setEnabled(enabled);
        addBtn.setEnabled(enabled);
        updateBtn.setEnabled(enabled);
        deleteBtn.setEnabled(enabled);
        searchTxFd.setEnabled(enabled);

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar6 = new javax.swing.JToolBar();
        refreshBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        searchTxFd = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        cardPane1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pagePane = new com.nazca.ui.pagination.PaginationPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableComp = new javax.swing.JTable();
        nWaitingPanel5 = new com.nazca.ui.NWaitingPanel();

        setLayout(new java.awt.BorderLayout());

        jToolBar6.setFloatable(false);
        jToolBar6.setRollover(true);
        jToolBar6.setPreferredSize(new java.awt.Dimension(240, 27));

        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/refresh_16.png"))); // NOI18N
        refreshBtn.setText("刷新");
        refreshBtn.setFocusable(false);
        refreshBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });
        jToolBar6.add(refreshBtn);

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/add_16.png"))); // NOI18N
        addBtn.setText("添加");
        addBtn.setFocusable(false);
        addBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        jToolBar6.add(addBtn);

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/update-16.png"))); // NOI18N
        updateBtn.setText("修改");
        updateBtn.setFocusable(false);
        updateBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jToolBar6.add(updateBtn);

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yz/ams/client/res/delete_16.png"))); // NOI18N
        deleteBtn.setText("删除");
        deleteBtn.setFocusable(false);
        deleteBtn.setMargin(new java.awt.Insets(2, 5, 2, 5));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        jToolBar6.add(deleteBtn);
        jToolBar6.add(filler2);

        searchTxFd.setMaximumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setMinimumSize(new java.awt.Dimension(150, 21));
        searchTxFd.setPreferredSize(new java.awt.Dimension(150, 21));
        jToolBar6.add(searchTxFd);

        add(jToolBar6, java.awt.BorderLayout.NORTH);

        jPanel10.setLayout(new java.awt.BorderLayout());

        cardPane1.setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(pagePane, java.awt.BorderLayout.PAGE_END);

        jScrollPane5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tableComp.setModel(new javax.swing.table.DefaultTableModel(
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
        tableComp.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tableComp);

        jPanel1.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        cardPane1.add(jPanel1, "CONTENT");
        cardPane1.add(nWaitingPanel5, "WAIT");

        jPanel10.add(cardPane1, java.awt.BorderLayout.CENTER);

        add(jPanel10, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        String keywords = searchTxFd.getText().trim();
        if (keywords.isEmpty()) {
            keywords = null;
        }
        TablePageSession page = pagePane.getPageSession();
        queryAttendanceAgent.setParameters(keywords, page.getCurPageNum(), page.getPageSize());
        queryAttendanceAgent.start();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        AddAttendancePanel editPane = new AddAttendancePanel();
        List<Attendance> attendancesLis = editPane.showMe(this);
        if (attendancesLis != null && attendancesLis.size() > 0 ) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        UpdateAttendancePanel editPane = new UpdateAttendancePanel();
        editPane.initPaneContent(curAttendance);
        Attendance attendance = editPane.showMe(this);
        if (attendance != null) {
            refreshBtnActionPerformed(evt);
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int row = tableComp.getSelectedRow();
        if (row >= 0) {
            row = tableComp.convertRowIndexToModel(row);
            Attendance data = tableModel.getData(row);
            USMSUser curUser = ClientContext.getUser();
            data.setModifierId(curUser.getId());
            data.setModifierName(curUser.getName());
            deleteAgent.setAttendance(data);
            DeleteOperationPanel<Attendance> deletePane = new DeleteOperationPanel<>(deleteAgent);
            deletePane.configSingleDelete("员工出勤姓名为", data.getUserName());
            Attendance att = deletePane.showMe(deleteBtn, ClientUtils.buildImageIcon("delete-attendence.png"), "删除出勤信息", 400, 150);
            if (att != null) {
                refreshBtnActionPerformed(null);
            }
        } else {
            UIUtilities.warningDlg(this, "请先选择要删除的出勤信息");
            return;
        }
    }//GEN-LAST:event_deleteBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JPanel cardPane1;
    private javax.swing.JButton deleteBtn;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar jToolBar6;
    private com.nazca.ui.NWaitingPanel nWaitingPanel5;
    private com.nazca.ui.pagination.PaginationPanel pagePane;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JTextField searchTxFd;
    private javax.swing.JTable tableComp;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables

    private AgentListener<PageResult<Attendance>> queryAttendanceAgentLis
            = new AgentListener<PageResult<Attendance>>() {
        @Override
        public void onStarted(long seq) {
            setCompsEnabled(false);
            nWaitingPanel5.setIndeterminate(true);
            nWaitingPanel5.showMsgMode("数据加载中，请稍后...", 0, NWaitingPanel.MSG_MODE_INFO);
            nWaitingPanel5.showWaitingMode();
            leftCard.show(CardLayoutWrapper.WAIT);
        }

        @Override
        public void onSucceeded(PageResult<Attendance> result, long seq) {
            if (result != null && result.getTotalCount() > 0) {//判断是否为空
                List<Attendance> list = result.getPageList();//数据集合
                int totalCount = result.getTotalCount();//当前页
                int pageSize = result.getPageSize();
                tableModel.setDatas(list);
                leftCard.show(CardLayoutWrapper.CONTENT);
                nWaitingPanel5.setIndeterminate(false);
                tableComp.getSelectionModel().setSelectionInterval(0, 0);
                pagePane.initPageButKeepSession(totalCount, pageSize);
            } else {
                updateBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                selectingVacation();
                nWaitingPanel5.showMsgMode("暂无出勤信息", 0, NWaitingPanel.MSG_MODE_INFO);
                leftCard.show(CardLayoutWrapper.WAIT);
            }
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
            searchTxFd.setEnabled(true);
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            refreshBtn.setEnabled(true);
            addBtn.setEnabled(true);
            searchTxFd.setEnabled(true);
            nWaitingPanel5.showMsgMode(errMsg, errCode, NWaitingPanel.MSG_MODE_ERROR);
            leftCard.show(CardLayoutWrapper.FAIL);
            nWaitingPanel5.setIndeterminate(false);
        }
    };

    public void init() {
        refreshBtnActionPerformed(null);
    }
}
