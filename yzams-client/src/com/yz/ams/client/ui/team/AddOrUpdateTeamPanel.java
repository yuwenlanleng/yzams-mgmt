/*
 * TeamPanel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-22 09:40:47
 */
package com.yz.ams.client.ui.team;

import com.nazca.ui.NComponentStyleTool;
import com.nazca.ui.NInternalDiag;
import com.nazca.ui.NInternalDiagListener;
import com.nazca.util.TimeFairy;
;
import com.yz.ams.client.agent.AddOrUpdateTeamAgent;
import com.yz.ams.client.agent.AgentListener;
import com.yz.ams.client.comp.OKCancelPanelListener;
import com.yz.ams.client.util.ClientUtils;
import com.yz.ams.model.Team;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author Your Name <Song Haixiang >
 */


public class AddOrUpdateTeamPanel extends javax.swing.JPanel {
    private AddOrUpdateTeamAgent addorUpdateTeamAgent = new AddOrUpdateTeamAgent();
    private Team dataSource = null;
    private boolean isAdd = true;

    /**
     * Creates new form TeamPanel
     */
    private void initAgentAndListener() {
        addorUpdateTeamAgent = new AddOrUpdateTeamAgent();
        addorUpdateTeamAgent.addListener(agentListener);
        oKCancelPanel1.addOKCancelListener(new OKCancelPanelListener() {
            @Override
            public void onOKClicked() {

                if (checkHolidayName()) {
                    String name = teamNameTxFd.getText().trim();
                    if (dataSource != null) {
                        dataSource.setTeamName(name);
                    } else {
                        dataSource = new Team();
                        dataSource.setTeamId("");
                        dataSource.setTeamName(name);
                    }
                    addorUpdateTeamAgent.setTeam(dataSource);
                    addorUpdateTeamAgent.start();
                }
            }

            @Override
            public void onCancelClicked() {
                NInternalDiag<Team, JComponent> diag = NInternalDiag.findNInternalDiag(
                        AddOrUpdateTeamPanel.this);
                diag.hideDiag();
            }
        });

    }

    public AddOrUpdateTeamPanel() {
        initComponents();
        initAgentAndListener();
    }

    //团队名称不为空和长度验证
    private boolean checkHolidayName() {
        String name = teamNameTxFd.getText().trim();
        if (name.isEmpty()) {
            oKCancelPanel1.gotoErrorMode("请输入团队名称!");
            NComponentStyleTool.errorStyle(teamNameTxFd);
            return false;
        } else if (name.length() > 15) {
            oKCancelPanel1.gotoErrorMode("团队名称最多只能输入15个字符!");
            NComponentStyleTool.errorStyle(teamNameTxFd);
            return false;
        } else {
            NComponentStyleTool.normalStyle(teamNameTxFd);
        }
        return true;
    }

    public Team showMe(JComponent parent) {
        NInternalDiag<Team, JComponent> diag = null;
        if (null == dataSource) {
            isAdd = true;
            diag = new NInternalDiag<>("添加团队", ClientUtils.buildImageIcon("add_team.png"), this);
        } else {
            isAdd = false;
            diag = new NInternalDiag<>("修改团队", ClientUtils.buildImageIcon("modify_team.png"), this);
        }
        diag.addNInternalDiagListener(new NInternalDiagListener() {

            @Override
            public void onClosing(NInternalDiag nid) {}

            @Override
            public void onClosed(NInternalDiag nid) {}

            @Override
            public void onShowingDone(NInternalDiag nid) {
                teamNameTxFd.requestFocusInWindow();
            }
        });
        return diag.showInternalDiag(parent, NInternalDiag.INIT_SIZE_MODE_PREFERED);
    }

    //修改团队时，初始化团队信息
    public void initPaneContent(Team team) {
        dataSource = team;
        if(team == null){
            teamNameTxFd.setText("");
        }else{
            teamNameTxFd.setText(team.getTeamName());
        }
        oKCancelPanel1.setOKButtonEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        teamNameTxFd = new javax.swing.JTextField();
        oKCancelPanel1 = new com.yz.ams.client.comp.OKCancelPanel();

        setRequestFocusEnabled(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 32, 24, 32));

        jLabel1.setText("团队名称：");

        teamNameTxFd.setMaximumSize(new java.awt.Dimension(260, 21));
        teamNameTxFd.setMinimumSize(new java.awt.Dimension(260, 21));
        teamNameTxFd.setPreferredSize(new java.awt.Dimension(260, 21));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamNameTxFd, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(teamNameTxFd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
        add(oKCancelPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private com.yz.ams.client.comp.OKCancelPanel oKCancelPanel1;
    private javax.swing.JTextField teamNameTxFd;
    // End of variables declaration//GEN-END:variables
     AgentListener<Team> agentListener = new AgentListener<Team>() {
        @Override
        public void onStarted(long seq) {
            teamNameTxFd.setEnabled(false);
            if (isAdd) {
                oKCancelPanel1.gotoWaitMode("正在添加...");
            } else {
                oKCancelPanel1.gotoWaitMode("正在修改...");
            }
        }

        @Override
        public void onSucceeded(Team result, long seq) {
            teamNameTxFd.setEnabled(true);
            if(isAdd){
                oKCancelPanel1.gotoSuccessMode("添加成功");
            }else{
                oKCancelPanel1.gotoSuccessMode("修改成功");
            }
            new Thread() {
                @Override
                public void run() {
                    new TimeFairy().sleepIfNecessary();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            NInternalDiag<Team, JComponent> diag = NInternalDiag.findNInternalDiag(
                                    AddOrUpdateTeamPanel.this);
                            diag.hideDiag(result);
                        }
                    });
                }
            }.start();
        }

        @Override
        public void onFailed(String errMsg, int errCode, long seq) {
            teamNameTxFd.setEnabled(true);
            oKCancelPanel1.gotoErrorMode(errMsg, errCode);
        }
    };
}