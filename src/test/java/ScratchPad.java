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
import java.awt.*;

public class ScratchPad {

    public static void main(String[] args) throws Exception {
        GitHubAPI api = new GitHubAPI("dragovorn", "dragon-bot-twitch", false);

        Dimension size = new Dimension(480, 140);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        JTextPane area = new JTextPane();
        area.setSize(size);
        area.setMaximumSize(size);
        area.setMinimumSize(size);
        area.setPreferredSize(size);
        area.setContentType("text/html");
        area.setEditable(false);
        area.setBorder(null);
        area.setBackground(UIManager.getColor("InternalFrame.background"));
        area.setText("<h1>" + api.getRelease("v1.05e").getString("name") + "</h1>" + Processor.process(api.getRelease("v1.05e").getString("body")));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setViewportBorder(null);
        scroll.setBorder(null);

        JLabel recommended = new JLabel("<html><b>Updating is always recommended!</b></html>");
        recommended.setForeground(Color.RED);

        JButton update = new JButton("Update!");
        JButton no = new JButton("Not now");

        JFrame frame = new JFrame("Update found!");
        frame.setSize(500, 200);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.add(scroll);
        frame.add(recommended);
        frame.add(no);
        frame.add(update);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}