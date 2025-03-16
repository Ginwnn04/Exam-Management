package GUI.Comp.Panel.Result;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import DTO.ResultDTO;
import GUI.Comp.Panel.PanelDetailExam;
import GUI.Comp.Swing.PanelBackground;
import style.ColorConfig;

public class PanelAfterExam extends PanelBackground {
    private final int WIDTH = 1200;
    private final int HEIGHT = 765;

    private ResultDTO result;
    private boolean isRenderTakeExamButton = true;

    private ArrayList<Runnable> backToPreviousClickCallbacks = new ArrayList<>();

    public PanelAfterExam(ResultDTO result) {
        this.result = result;
        initComponents();
    }

    public PanelAfterExam(ResultDTO result, boolean isRenderTakeExamButton) {
        this.result = result;
        this.isRenderTakeExamButton = isRenderTakeExamButton;

        initComponents();
    }

    public void addOnBackToPreviousClickCallback(Runnable runnable) {
        backToPreviousClickCallbacks.add(runnable);
    }

    public void onBackToPreviousClick() {
        for (var callback : backToPreviousClickCallbacks) {
            callback.run();
        }
    }

    private void initComponents() {
        setAbsoluteSize(WIDTH, HEIGHT);
        setBackground(ColorConfig.GREY_COLOR_BG);
        setLayout(new GridBagLayout());

        content = new PanelBackground();
        content.setAbsoluteSize(1160, HEIGHT - 40);
        content.setLayout(new BorderLayout());

        panelTestScore = new PanelTestScore(result, isRenderTakeExamButton);
        panelTestScore.addOnBackToPreviousClickCallback(() -> onBackToPreviousClick());
        panelTestScore.addOnChangeTabCallback(this::handleChangeTab);

        panelResult = new PanelDetailExam(result);
        panelResult.addOnChangeTabCallback(this::handleChangeTab);
        
        content.add(panelTestScore);
        add(content);
    }

    private void handleChangeTab(JPanel oldContent) {
        content.remove(oldContent);

        if (oldContent instanceof PanelTestScore) {
            content.add(panelResult);
        }
        else content.add(panelTestScore);

        content.revalidate();
        content.repaint();

        revalidate();
        repaint();
    }

    private PanelTestScore panelTestScore;
    private PanelDetailExam panelResult;
    private PanelBackground content;
}