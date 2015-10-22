package ClassesTeste.utils;
import java.awt.*;
import java.beans.*;
import java.io.File;
import javax.swing.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* PreviewImagem.java by FileChooserDemo2.java. */
public class PreviewImagem extends JComponent
                          implements PropertyChangeListener {
    ImageIcon thumbnail = null;
    File file = null;
    int tamanhoH, tamanhoV;

    public PreviewImagem(JFileChooser fc, int h, int v) {
        setPreferredSize(new Dimension(h,v));
        tamanhoH=h;
        tamanhoV=v;
        fc.addPropertyChangeListener(this);
    }

    public PreviewImagem(JLabel jlb,File f, int h, int v){
      file=f;
      setPreferredSize(new Dimension(h,v));
        tamanhoH=h;
        tamanhoV=v;
      jlb.add(this);
    }
    
    
    
    public void loadImage() {
        if (file == null) {
            thumbnail = null;
            return;
        }

        //Don't use createImageIcon (which is a wrapper for getResource)
        //because the image we're trying to load is probably not one
        //of this program's own resources.
        ImageIcon tmpIcon = new ImageIcon(file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > tamanhoH) {
                thumbnail = new ImageIcon(tmpIcon.getImage().
                                          getScaledInstance(tamanhoH, -1,
                                                      Image.SCALE_DEFAULT));
            } else { //no need to miniaturize
                thumbnail = tmpIcon;
            }
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        boolean update = false;
        String prop = e.getPropertyName();

        //If the directory changed, don't show an image.
        if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
            file = null;
            update = true;

        //If a file became selected, find out which one.
        } else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
            file = (File) e.getNewValue();
            update = true;
        }

        //Update the preview accordingly.
        if (update) {
            thumbnail = null;
            if (isShowing()) {
                loadImage();
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g) {
        if (thumbnail == null) {
            loadImage();
        }
        if (thumbnail != null) {
            int x = getWidth()/2 - thumbnail.getIconWidth()/2;
            int y = getHeight()/2 - thumbnail.getIconHeight()/2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}




