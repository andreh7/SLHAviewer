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

import edu.ucsd.hep.slhaviewer.dataformat.DecayBlock;
import edu.ucsd.hep.slhaviewer.dataformat.MassBlock;
import edu.ucsd.hep.slhaviewer.dataformat.SLHAdata;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Frame for displaying particle decay data.
 * 
 * For the moment we put the list of particles on the left (to be selected
 * from a JList on the left) and the information about this particle
 * on the right.
 * 
 * @author holzner
 */
public class DecayViewerPanel extends JPanel
{
  // private final DefaultListModel particleSelectionListModel;
  
  /** the list of particles, ordered as to be shown in the list on the left
   *  in the panel.
   */
  private final List<DecayBlock> decayList = new ArrayList<DecayBlock>();
  private final List<Double> massList = new ArrayList<Double>();
  
  private final JList particleSelectionList;
  private final JLabel particleNamePanel;
  private final JLabel particleWidthPanel;
  private final JLabel particleMassPanel;
  private final DecaysTableModel detailTableModel;
  private final TableRowSorter<TableModel> sorter;
  private final JTable detailTable;
  
  //----------------------------------------------------------------------

  public DecayViewerPanel()
  {
    this.setLayout(new BorderLayout());
    
    particleSelectionList = new JList();
    
    // make sure we can only select one particle at a time
    particleSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    particleSelectionList.addListSelectionListener(new ListSelectionListener(){

      public void valueChanged(ListSelectionEvent e)
      {
        if (e.getValueIsAdjusting())
          return;
        
        selectParticle(particleSelectionList.getSelectedIndex());
      }
     
    });
    
    this.add(new JScrollPane(particleSelectionList), BorderLayout.WEST);

    //----------
    // panel on top
    //----------
    
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
    
    JPanel topPanel = new JPanel();
    
    topPanel.add(new JLabel("particle:"));
    particleNamePanel = new JLabel("-");
    topPanel.add(particleNamePanel);

    topPanel.add(new JLabel(" mass [GeV]:"));
    particleMassPanel = new JLabel("-");
    topPanel.add(particleMassPanel);
    
    topPanel.add(new JLabel(" width [GeV]:"));
    particleWidthPanel = new JLabel("-");
    topPanel.add(particleWidthPanel);
    
    centerPanel.add(topPanel, BorderLayout.NORTH);
    
    //----------
    // table on the right
    //----------

     detailTable = new JTable();
    detailTableModel = new DecaysTableModel();
    detailTable.setModel(detailTableModel);
    
    // enable table sorting
    sorter = new TableRowSorter<TableModel>(detailTable.getModel());
    
    centerPanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);
    this.add(centerPanel, BorderLayout.CENTER);
    
  }

  //----------------------------------------------------------------------
  /** this is called when a new element is selected in the left list */
  private void selectParticle(int index)
  {
    // update information
    DecayBlock decay = this.decayList.get(index);
  
    this.particleNamePanel.setText(decay.getMother().verboseName);
    this.particleWidthPanel.setText("" + decay.getDecayWidth());
  
    Double mass = this.massList.get(index);
    if (mass == null)
      // not contained in this SLHA file
        this.particleMassPanel.setText("unknown");
    else
      this.particleMassPanel.setText("" + mass);
  
    // update detailed table model
    this.detailTableModel.setDecay(decay);

    // looks like we have to do this every time a new particle is selected ?!
    // (otherwise sorting seemed not to work...)
    detailTable.setRowSorter(sorter);
  }

   //----------------------------------------------------------------------
  public void setDecayData(SLHAdata slhaData)
  {
    synchronized (this.decayList)
    {
      List<DecayBlock> decays = new ArrayList<DecayBlock>(slhaData.getDecays().values());
      Collections.sort(decays, new DecayBlock.AbsPdgIDComparator());

      // set the list of particles in the left list
      this.decayList.clear();
      this.massList.clear();
      
      String labels[] = new String[decays.size()];
      
      int i = 0;
      for (DecayBlock decay : decays)
      {
        decayList.add(decay);
        
        // see if we can get the masses
        this.massList.add(slhaData.getParticleMass(decay.getMother().pdgId));
        
        labels[i++] = decay.getMother().shortName;
      }
      
      this.particleSelectionList.setListData(labels);
    
      //----------
      
      // update the table model for the right side
    
    }
    
  }
  
  //----------------------------------------------------------------------

}
