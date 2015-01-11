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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A panel for viewing cross sections.
 * 
 * For the moment, we show all cross section entries of all final states
 * in one table (e.g. to be able to compare cross sections of different
 * final states).
 * 
 * @author holzner
 */
public class CrossSectionViewerPanel extends JPanel
{
  private final CrossSectionTableModel tableModel;

  //----------------------------------------------------------------------

  public CrossSectionViewerPanel()
  {
    tableModel = new CrossSectionTableModel();
    
    this.setLayout(new BorderLayout());
    
    JTable table = new JTable();
    table.setModel(tableModel);
    
    this.add(new JScrollPane(table), BorderLayout.CENTER);
  }
  
  //----------------------------------------------------------------------

  public void setCrossSections(SLHAdata slhaData)
  {
    this.tableModel.setCrossSections(slhaData);
  }

  //----------------------------------------------------------------------

}
