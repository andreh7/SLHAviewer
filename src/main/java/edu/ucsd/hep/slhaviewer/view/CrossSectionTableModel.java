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

import edu.ucsd.hep.slhaviewer.Particle;
import edu.ucsd.hep.slhaviewer.dataformat.SLHAdata;
import edu.ucsd.hep.slhaviewer.dataformat.XsectBlock;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * A table model for viewing cross section information
 * @author holzner
 */
public class CrossSectionTableModel extends AbstractTableModel
{
  private List<RowEntry> rows = new ArrayList<RowEntry>();

  //----------------------------------------------------------------------

  private enum Columns
  {
    COL_PROCESS,
    COL_SQRTS,
    COL_SCALE_SCHEME,
    COL_QCD_ORDER,
    COL_EW_ORDER,
    COL_KAPPA_FACT,
    COL_KAPPA_RENORM,
    COL_PDF_ID,
    COL_XSECT,
    COL_PROG_NAME,
    COL_PROG_VERSION,
  }
  
  private Columns[] selectedColumns = Columns.values();

  //----------------------------------------------------------------------
  
  private static class RowEntry
  {
    private String process;
    private double sqrts;
    private XsectBlock.Entry entry;
    
  }
  
  //----------------------------------------------------------------------

  public int getRowCount()
  {
    return rows.size();
  }
  
  //----------------------------------------------------------------------

  public int getColumnCount()
  {
    return Columns.values().length;
  }
  
  //----------------------------------------------------------------------

  public Object getValueAt(int rowIndex, int columnIndex)
  {
    RowEntry row = this.rows.get(rowIndex);
        
    switch (selectedColumns[columnIndex])
    {
      case COL_PROCESS: return row.process;
      
      case COL_SQRTS: return row.sqrts;  
        
      case COL_SCALE_SCHEME: return row.entry.scaleScheme;
    
      case COL_QCD_ORDER: return row.entry.qcdOrder;
      
      case COL_EW_ORDER: return row.entry.ewOrder;
      
      case COL_KAPPA_FACT: return row.entry.kappaFactorization;
      
      case COL_KAPPA_RENORM: return row.entry.kappaRenormalization;

      case COL_PDF_ID: return row.entry.pdfId;
      
      case COL_XSECT: return row.entry.xsect;
      
      case COL_PROG_NAME: return row.entry.programName;
      
      case COL_PROG_VERSION: return row.entry.programVersion;
      
      default: return "???";
    } // switch
    
  }
  
  //----------------------------------------------------------------------

  void setCrossSections(SLHAdata slhaData)
  {
    synchronized (this.rows)
    {
      this.rows.clear();
      
      for (XsectBlock xsectBlock : slhaData.getCrossSections())
      {
        for (XsectBlock.Entry entry : xsectBlock.getEntries())
        {
          RowEntry row = new RowEntry();
          
          row.process = xsectBlock.getBeamParticle(0).shortName + " " + 
                 xsectBlock.getBeamParticle(1).shortName + " >";
          
          row.sqrts = xsectBlock.getSqrts();
          
          for (Particle fsParticle : xsectBlock.getFinalStateParticles())
            row.process += " " + fsParticle.shortName;

          row.entry = entry;
                    
          this.rows.add(row);
        } // loop over entries in this cross section block
        
      } // loop over cross section blocks
      
      
      this.fireTableDataChanged();
    } // synchronized
  }
  
  //----------------------------------------------------------------------

  @Override
  public Class<?> getColumnClass(int columnIndex)
  {
    switch (selectedColumns[columnIndex])
    {
      case COL_PROCESS: 
      case COL_PROG_NAME: 
      case COL_PROG_VERSION:
        return String.class;
      
      case COL_SQRTS: 
      case COL_KAPPA_FACT: 
      case COL_KAPPA_RENORM: 
      case COL_XSECT: 
        return Double.class;
      
      case COL_QCD_ORDER: 
      case COL_EW_ORDER: 
      case COL_SCALE_SCHEME: 
      case COL_PDF_ID: 
        return Integer.class;
      
      default: return super.getColumnClass(columnIndex);
    }
  }

  //----------------------------------------------------------------------

  @Override
  public String getColumnName(int columnIndex)
  {
    switch (selectedColumns[columnIndex])
    {
      case COL_PROCESS: return "process";
      
      case COL_SQRTS: return "sqrts [GeV]";  
        
      case COL_SCALE_SCHEME: return "scale scheme";
    
      case COL_QCD_ORDER: return "QCD order";
      
      case COL_EW_ORDER: return "Electroweak order";
      
      case COL_KAPPA_FACT: return "kappa (factorization scale)";
      
      case COL_KAPPA_RENORM: return "kappa (renormalization scale)";

      case COL_PDF_ID: return "pdf";
      
      case COL_XSECT: return "cross section [pb]";
      
      case COL_PROG_NAME: return "program name";
      
      case COL_PROG_VERSION: return "program version";
      
      default: return "???";
    } // switch
  }

  //----------------------------------------------------------------------

  
  
}
