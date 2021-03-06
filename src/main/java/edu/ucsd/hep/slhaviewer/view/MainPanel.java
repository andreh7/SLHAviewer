/*
 * Copyright 2015 University of California, San Diego.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.ucsd.hep.slhaviewer.view;

import edu.ucsd.hep.slhaviewer.dataformat.SLHAdata;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * the main Panel for the application
 * @author holzner
 */
public class MainPanel extends JPanel
{
  private final JTabbedPane tabs;
  
  private final JLabel labelNoBlocksFound;
  
  private boolean tabsAdded;  
  
  //----------------------------------------------------------------------

  public MainPanel()
  {
    this.setLayout(new BorderLayout());
    
    tabs = new JTabbedPane();    
    labelNoBlocksFound = new JLabel("no supported blocks found", SwingConstants.CENTER);
    
    this.addRemoveTabGroup(true);
  }
  
  //----------------------------------------------------------------------

  private void addRemoveTabGroup(boolean add)
  {
    if (add == tabsAdded)
      // nothing to do
      return;
    
    if (add)
    {
      this.remove(this.labelNoBlocksFound);
      this.add(tabs, BorderLayout.CENTER);
    }
    else
    {
      this.remove(this.tabs);
      this.add(this.labelNoBlocksFound, BorderLayout.CENTER);
    }
    
    this.tabsAdded = add;
  }
  
  //----------------------------------------------------------------------

  public void setData(SLHAdata data)
  {
    this.tabs.removeAll();
    
    // create the tabs
    if (data.hasMasses())
    {
      this.tabs.add("mass table", MassViewerPanel.make(data));
      this.tabs.add("mass graph", MassGraphPanel.make(data));
    }

    if (data.hasDecays())
      this.tabs.add("decays", DecayViewerPanel.make(data));

    if (data.hasCrossSections())
      this.tabs.add("cross sections", CrossSectionViewerPanel.make(data));
    
    if (this.tabs.getComponentCount() == 0)
      // no supported blocks found
      this.addRemoveTabGroup(false);
    else
      this.addRemoveTabGroup(true);
    
  }

  //----------------------------------------------------------------------

}
