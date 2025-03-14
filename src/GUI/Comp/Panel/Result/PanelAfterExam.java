package GUI.Comp.Panel.Result;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import BUS.ResultBUS;
import DTO.ResultDTO;
import GUI.Comp.Dialog.Statistics.PanelChart;
import GUI.Comp.Panel.PanelDetailExam;
import GUI.Comp.Swing.PanelBackground;
import style.ColorConfig;

public class PanelAfterExam extends PanelBackground {
    private final int WIDTH = 1200;
    private final int HEIGHT = 765;

    private ResultDTO result;

    public PanelAfterExam(ResultDTO result) {
        this.result = result;
        initComponents();
    }

    private void initComponents() {
        setAbsoluteSize(WIDTH, HEIGHT);
        setBackground(ColorConfig.GREY_COLOR_BG);
        setLayout(new GridBagLayout());

        panelTestScore = new PanelTestScore(result);
        panelTestScore.addOnChangeTabCallback(this::handleChangeTab);

        panelResult = new PanelDetailExam(result);
        panelResult.addOnChangeTabCallback(this::handleChangeTab);
        
        add(panelTestScore);
    }

    private void handleChangeTab(JPanel oldContent) {
        remove(oldContent);

        if (oldContent instanceof PanelTestScore) {
            add(panelResult);
        }
        else add(panelTestScore);

        revalidate();
        repaint();
    }

    private PanelTestScore panelTestScore;
    private PanelDetailExam panelResult;
}