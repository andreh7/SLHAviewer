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
package edu.ucsd.hep.slhaviewer.dataformat;

import edu.ucsd.hep.slhaviewer.Particle;
import edu.ucsd.hep.slhaviewer.Particles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Corresponds to a DECAY block, contains information about a particle's
 * decay modes.
 * @author holzner
 */
public class DecayBlock
{
  private final Particle mother;

  private final List<DecayMode> decayModes = new ArrayList<DecayMode>();
  private final double decayWidth;


  //----------------------------------------------------------------------

  DecayBlock(List<Reader.LineEntry> lines)
  {
    // get the first line
    Reader.LineEntry firstLine = lines.remove(0);
    
    int pdgId = Integer.parseInt(firstLine.parts.get(1));
    
    this.mother = Particles.get(pdgId);

    this.decayWidth = Double.parseDouble(firstLine.parts.get(2));
    
    // read decay modes
    for (Reader.LineEntry line : lines)
    {
      if (line.isEmpty)
        continue;
      
      double branchingRatio = Double.parseDouble(line.parts.get(0));
      
      int numDaughters = Integer.parseInt(line.parts.get(1));
      
      List<Particle> daughters = new ArrayList<Particle>();
      
      for (int i = 0; i < numDaughters; ++i)
      {
        int id = Integer.parseInt(line.parts.get(2 + i));
        daughters.add(Particles.get(id));
      }
      
      decayModes.add(new DecayMode(branchingRatio, Collections.unmodifiableList(daughters)));
      
      
    } // loop over all decay modes
    
  }

  //----------------------------------------------------------------------

  public Particle getMother()
  {
    return mother;
  }
  //----------------------------------------------------------------------

  public List<DecayMode> getDecayModes()
  {
    return decayModes;
  }
  //----------------------------------------------------------------------

  public double getDecayWidth()
  {
    return decayWidth;
  }

}
