package GUI.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintsBuilder {
    private GridBagConstraints gbc;

    public GridBagConstraintsBuilder(){
        gbc = new GridBagConstraints();
    }

    public GridBagConstraintsBuilder(GridBagConstraints gbc){
        this.gbc = gbc;
    }

    public GridBagConstraintsBuilder setPosition(int x, int y){
        gbc.gridx = x;
        gbc.gridy = y;

        return this;
    }

    public GridBagConstraintsBuilder setAnchor(int anchor){
        gbc.anchor = anchor;
        return this;
    }

    public GridBagConstraintsBuilder setSize(int gridWidth, int gridHeight){
        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;

        return this;
    }

    /**
     * 
     * @param fill GridBagConstraints item
     */
    public GridBagConstraintsBuilder setFill(int fill){
        gbc.fill = fill;
        return this;
    }

    public GridBagConstraintsBuilder setWeights(int weightX, int weightY){
        gbc.weightx = weightX;
        gbc.weighty = weightY;

        return this;
    }

    public GridBagConstraintsBuilder setInsets(int top, int left, int bottom, int right){
        gbc.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GridBagConstraints result(){
        return gbc;
    }

    public void reset(){
        gbc = new GridBagConstraints();
    }
}
