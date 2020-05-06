package io.swingsnackbar.model;

import io.swingsnackbar.action.AbstractSnackBarAction;
import io.swingsnackbar.SnackBar;
import io.swingsnackbar.view.BasicSnackBarUI;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class SnackBarContainer extends JPanel {

    /**
     * With this code you can load the personal UI with Swing system call
     */
    private static final String uiClassID = "SnackBarUI";

    protected JLabel snackBarText;
    protected JLabel snackBarIcon;
    protected SnackBar snackBar;
    protected int gap = 30;

    private SnackBarContainer(SnackBar snackBar) {
        super(new BorderLayout());
        this.snackBar = snackBar;
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText, JLabel snackBarIcon) {
        this(snackBar);
        if (snackBarText == null || snackBarIcon == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
        this.snackBarIcon = snackBarIcon;
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText, Icon snackBarIcon) {
        this(snackBar);
        if (snackBarText == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarIcon.setHorizontalTextPosition(SwingConstants.LEFT);
        this.snackBarText = snackBarText;
        if (snackBarIcon != null) {
            this.snackBarIcon = new JLabel();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.snackBarIcon.setIcon(snackBarIcon);
        }
    }

    public SnackBarContainer(SnackBar snackBar, JLabel snackBarText) {
        this(snackBar);
        if (snackBarText == null) {
            throw new IllegalArgumentException("TODO complex message, for the moment the function arguments are null");
        }
        this.snackBarText = snackBarText;
    }

    @Override
    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            PanelUI ui = (PanelUI) UIManager.getUI(this);
            setUI(ui);
        } else {
            PanelUI ui = new BasicSnackBarUI();
            setUI(ui);
        }
    }

    /**
     * This was call only inside the component SnackBar, to set the style inside the component
     * param owner
     */
    public void inflateContent() {
        super.add(snackBarText, BorderLayout.WEST);
        if(snackBarIcon != null){
            super.add(snackBarIcon, BorderLayout.EAST);
        }
        setVisible(true);
    }


    public Dimension getDimension() {
        int width;
        if (this.snackBarIcon != null && snackBarIcon.getIcon() != null) {
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText())
                    + this.snackBarIcon.getIcon().getIconWidth();
        } else {
            this.snackBarText.setHorizontalTextPosition(SwingConstants.CENTER);
            width = this.snackBarText.getFontMetrics(
                    this.snackBarText.getFont()).stringWidth(this.snackBarText.getText());
            if(this.snackBarIcon != null){
                this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
                width = this.snackBarText.getFontMetrics(
                        this.snackBarText.getFont()).stringWidth(this.snackBarText.getText());
                width += (this.snackBarIcon.getText() != null
                        ? this.snackBarIcon.getText().length() * 8
                        : 10);
            }
        }
        width += gap;
        return new Dimension(width, 50);
    }

    public void setAction(AbstractSnackBarAction action) {
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.updateUI();
        }
        this.snackBarIcon.addMouseListener(action);
    }

    public void setIcon(Icon icon) {
        if(this.snackBarIcon == null){
            this.snackBarIcon = new JLabel();
            super.add(snackBarIcon, BorderLayout.EAST);
            this.snackBar.initStyle();
            this.snackBarIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
            this.updateUI();
        }
        this.snackBarIcon.setIcon(icon);
    }

    public void setGap(int gap){
        this.gap = gap;
        this.snackBar.revalidate();
    }

    public void setText(String text) {
        this.snackBarText.setText(text);
    }

    public void setIconTextColor(Color color){
        if(snackBarIcon.getIcon() == null){
            this.snackBarIcon.setForeground(color);
        }
    }

    public void setIconTextStyle(Font font) {
        if(snackBarIcon.getIcon() == null){
            this.snackBarIcon.setFont(font);
        }
    }

    //getter and setter
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    public JLabel getSnackBarText() {
        return snackBarText;
    }

    public JLabel getSnackBarIcon() {
        return snackBarIcon;
    }

    public SnackBar getSnackBar() {
        return snackBar;
    }
}
