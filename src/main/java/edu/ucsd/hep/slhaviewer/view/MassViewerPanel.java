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

import edu.ucsd.hep.slhaviewer.Particles;
import edu.ucsd.hep.slhaviewer.dataformat.MassBlock;
import edu.ucsd.hep.slhaviewer.dataformat.SLHAdata;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * class to view the masses of the particles 
 *
 * @author holzner
 */
public class MassViewerPanel extends JPanel
{

  private final DefaultTableModel tableModel;
    
  //----------------------------------------------------------------------

  public MassViewerPanel()
  {
    // for the moment, just display a table with the masses
    // (can add the widths later but then the class should be renamed)

    this.setLayout(new BorderLayout());
    
    JTable table = new JTable();
    
    // generate a simple table for the moment
    tableModel = new DefaultTableModel();
    tableModel.addColumn("id");
    tableModel.addColumn("particle");
    tableModel.addColumn("short name");
    tableModel.addColumn("mass [GeV]");

    table.setModel(tableModel);
    
    this.add(new JScrollPane(table), BorderLayout.CENTER);
  } 
  
  //----------------------------------------------------------------------
  
  public static MassViewerPanel make(SLHAdata data)
  {
    MassViewerPanel retval = new MassViewerPanel();
    retval.setParticles(data);
    return retval;
  }

  //----------------------------------------------------------------------
  
  public void setParticles(SLHAdata slhaData)
  {
    MassBlock massBlock = slhaData.getMassBlock();
    
    // sort by PDGid
    List<Integer> pdgIds = new ArrayList<Integer>(massBlock.getParticleIds());
    Collections.sort(pdgIds);
    
    int row = 0;

    tableModel.setNumRows(pdgIds.size());
    
    for (int pdgId : pdgIds)
    {
      tableModel.setValueAt(pdgId, row, 0);
      tableModel.setValueAt(Particles.get(pdgId).verboseName, row, 1);
      tableModel.setValueAt(Particles.get(pdgId).shortName, row, 2);
      tableModel.setValueAt(massBlock.getMass(pdgId), row, 3);
      
      // prepare next iteration
      ++row;
    }
  }
  
  //----------------------------------------------------------------------

}
