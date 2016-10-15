/*
 * Copyright (c) 2016. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import com.dragovorn.dragonbot.api.github.GitHubAPI;
import com.github.rjeschke.txtmark.Processor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ScratchPad {

    public static void main(String[] args) throws Exception {
        GitHubAPI api = new GitHubAPI("dragovorn", "dragon-bot-twitch", false);

        JTextPane area = new JTextPane();
        area.setEditorKit(new WrapEditorKit());
        area.setSize(20, 20);
        area.setContentType("text/html");
        area.setEditable(false);
        area.setText(Processor.process(api.getRelease("v1.05e").getString("body")));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton button = new JButton("test");

        JFrame frame = new JFrame("Update found!");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.add(scroll);
        frame.add(button);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static class WrapEditorKit extends StyledEditorKit {
        ViewFactory defaultFactory=new WrapColumnFactory();
        public ViewFactory getViewFactory() {
            return defaultFactory;
        }

    }

    static class WrapColumnFactory implements ViewFactory {
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new WrapLabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }

    static class WrapLabelView extends LabelView {
        public WrapLabelView(Element elem) {
            super(elem);
        }

        public float getMinimumSpan(int axis) {
            switch (axis) {
                case View.X_AXIS:
                    return 0;
                case View.Y_AXIS:
                    return super.getMinimumSpan(axis);
                default:
                    throw new IllegalArgumentException("Invalid axis: " + axis);
            }
        }

    }
}