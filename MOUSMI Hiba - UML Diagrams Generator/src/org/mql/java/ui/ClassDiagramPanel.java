package org.mql.java.ui;

import javax.swing.*;

import org.mql.java.models.AnnotationEntity;
import org.mql.java.models.ClassEntity;
import org.mql.java.models.EnumEntity;
import org.mql.java.models.InterfaceEntity;
import org.mql.java.models.PackageEntity;
import org.mql.java.models.RelationEntity;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDiagramPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<PackageEntity> packages;
    private final Map<String, Point> componentLocations;
    private int panelWidth;
    private int panelHeight;

    public ClassDiagramPanel(List<PackageEntity> packages) {
        this.packages = packages;
        this.componentLocations = new HashMap<>();
        setPreferredSize(new Dimension(1000, 600));
      
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xOffset = 10;
        int rowHeight = 190;
        int rowY = 50;

        for (PackageEntity pkg : packages) {
            for (ClassEntity cls : pkg.getClasses()) {
                drawComponent(g, "", cls.getName(), xOffset, rowY);
                drawClassDetails(g, cls, xOffset, rowY + 2);
                xOffset += 250;
            }

            for (InterfaceEntity iface : pkg.getInterfaces()) {
                drawComponent(g, "<<Interface>>", iface.getName(), xOffset, rowY);
                drawInterfaceDetails(g, iface, xOffset, rowY + 2);
                xOffset += 250;
            }

            for (EnumEntity enumEntity : pkg.getEnums()) {
                drawComponent(g, "<<Enum>>", enumEntity.getName(), xOffset, rowY);
                drawEnumDetails(g, enumEntity, xOffset, rowY + 2);
                xOffset += 250;
            }

            for (AnnotationEntity annotation : pkg.getAnnotations()) {
                drawComponent(g, "<<Annotation>>", annotation.getName(), xOffset, rowY);
                drawAnnotationDetails(g, annotation, xOffset, rowY + 2);
                xOffset += 250;
            }

            if (!pkg.getClasses().isEmpty()) {
                rowY += rowHeight;
                xOffset = 20;
            }
        }

        ensureNoOverlap();
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        
    }

    private void drawComponent(Graphics g, String type, String name, int x, int y) {
        g.drawRect(x, y, 200, 20);
        g.setColor(Color.BLACK);
        g.drawString(type + " " + name, x + 10, y + 15);
        componentLocations.put(name, new Point(x, y));
    }

    private void drawClassDetails(Graphics g, ClassEntity cls, int x, int y) {
        int detailsX = x;
        int detailsY = y;

        for (ClassEntity.Attribute attribute : cls.getAttributes()) {
            String accessModifier = getAccessModifierSymbol(attribute.getAccessModifier());
            drawComponent(g, accessModifier + " " + attribute.getAttributeName(), " : " + attribute.getType(), detailsX, detailsY + 20);
            detailsY += 20;
        }

        for (ClassEntity.Method method : cls.getMethods()) {
            String accessModifier = getAccessModifierSymbol(method.getAccessModifier());
            drawComponent(g, accessModifier + " " + method.getMethodName(), "() : " + method.getReturnType(), detailsX, detailsY + 20);
            detailsY += 20;
        }
    }

    private String getAccessModifierSymbol(String accessModifier) {
        if (accessModifier.contains("public")) {
            return "+";
        } else if (accessModifier.contains("private")) {
            return "-";
        } else if (accessModifier.contains("protected")) {
            return "#";
        } else {
            return "~";
        }
    }

    private void drawInterfaceDetails(Graphics g, InterfaceEntity iface, int x, int y) {
        int detailsX = x;
        int detailsY = y;

        for (InterfaceEntity.Method method : iface.getMethods()) {
            String accessModifier = getAccessModifierSymbol(method.getAccessModifier());
            drawComponent(g, accessModifier, method.getMethodName() + "() : " + method.getReturnType(), detailsX, detailsY + 20);
            detailsY += 20;
        }
    }

    private void drawEnumDetails(Graphics g, EnumEntity enumEntity, int x, int y) {
        int detailsX = x;
        int detailsY = y;

        for (String constant : enumEntity.getConstants()) {
            drawComponent(g, "", constant, detailsX, detailsY + 20);
            detailsY += 20;
        }
    }

    private void drawAnnotationDetails(Graphics g, AnnotationEntity annotation, int x, int y) {
        int detailsX = x;
        int detailsY = y;

        for (AnnotationEntity.AnnotationMethod method : annotation.getMethods()) {
            drawComponent(g, "-", method.getName() + " : " + method.getType(), detailsX, detailsY + 20);
            detailsY += 20;
        }
    }

    private void drawAssociations(Graphics g, PackageEntity pkg, int rowY) {
        for (ClassEntity cls : pkg.getClasses()) {
            List<RelationEntity> relations = cls.getRelations();
            for (RelationEntity association : relations) {
                Point start = componentLocations.get(cls.getName());
                Point end = componentLocations.get(association.getTarget());
                drawAssociation(g, start, end);
            }
        }
    }

    private void drawAssociation(Graphics g, Point start, Point end) {
        g.drawLine(start.x + 200, start.y + 25, end.x, end.y + 25);
        int[] arrowX = {end.x, end.x - 10, end.x - 10};
        int[] arrowY = {end.y + 25, end.y, end.y + 50};
        g.fillPolygon(arrowX, arrowY, 3);
    }

    private void ensureNoOverlap() {
        for (Map.Entry<String, Point> entry : componentLocations.entrySet()) {
            Point location = entry.getValue();
            if (location.x + 250 > panelWidth) {
                panelWidth = location.x + 250;
            }
            if (location.y + 150 > panelHeight) {
                panelHeight = location.y + 150;
            }
        }
    }
}
