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
import edu.ucsd.hep.slhaviewer.dataformat.DecayBlock;
import edu.ucsd.hep.slhaviewer.dataformat.DecayMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * A JTable model for viewing branching ratios of decays
 * @author holzner
 */
public class DecaysTableModel extends AbstractTableModel
{
  private final List<RowEntry> rows = new ArrayList<RowEntry>();
  
  private int maxNumChildren = 0;

  private final int COL_BRANCHING_RATIO = 0;
  private final int COL_FIRST_DAUGHTER = 1;
  
  //----------------------------------------------------------------------

  private static class RowEntry
  {
    double branchingRatio;
    List<String> daughterNames = new ArrayList<String>();
  }
  
  //----------------------------------------------------------------------

  public void setDecay(DecayBlock decay) 
  {
    synchronized (this.rows)
    {
      // extract information to be displayed
      this.rows.clear();
      this.maxNumChildren = 0;
      
      for (DecayMode mode : decay.getDecayModes())
      {
        RowEntry entry = new RowEntry();

        entry.branchingRatio = mode.branchingRatio;

        // update the maximum number of children to be displayed
        this.maxNumChildren = Math.max(maxNumChildren, mode.daughters.size());
        for (Particle daughter : mode.daughters)
        {
          entry.daughterNames.add(daughter.shortName);
        }

        this.rows.add(entry);

      } // loop over all decay modes 

      // by default sort in decreasing order of branching ratio
      Collections.sort(rows, new Comparator<RowEntry>(){
        /** note the inverse naming of the parameters to get a decreasing order */
        public int compare(RowEntry r2, RowEntry r1)
        {
          return Double.compare(r1.branchingRatio, r2.branchingRatio);
        }
      });
      
      // we may have a different number of columns than before
      this.fireTableStructureChanged();
      this.fireTableDataChanged();
    }
  }

  //----------------------------------------------------------------------

  public int getRowCount()
  {
    return this.rows.size();
  }
  
  //----------------------------------------------------------------------

  public int getColumnCount()
  {
    return 1 + this.maxNumChildren;
  }
  
  //----------------------------------------------------------------------

  public Object getValueAt(int row, int column)
  {
    RowEntry entry = this.rows.get(row);
    
    switch (column)
    {
      case COL_BRANCHING_RATIO: return entry.branchingRatio;
      default:
      {
        int daughterIndex = column - COL_FIRST_DAUGHTER;
        if (daughterIndex < entry.daughterNames.size())
          return entry.daughterNames.get(daughterIndex);
        else
          return null;
      }
    }
  }
  
  //----------------------------------------------------------------------

  @Override
  public Class<?> getColumnClass(int column)
  {
    switch (column)
    {
      case COL_BRANCHING_RATIO: return Double.class;
      default:
        return String.class;
    }
  }

  //----------------------------------------------------------------------
  
  @Override
  public String getColumnName(int column)
  {
    switch (column)
    {
      case COL_BRANCHING_RATIO: return "branching ratio";
      default:
        return "daughter " + (column - COL_FIRST_DAUGHTER);
    }
  }
  
  //----------------------------------------------------------------------

}
