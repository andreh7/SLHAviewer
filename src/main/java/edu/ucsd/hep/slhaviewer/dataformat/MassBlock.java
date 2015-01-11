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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * block containing particle masses
 * @author holzner
 */
public class MassBlock
{
  /** maps from pdgId to mass of the particle */
  private final Map<Integer, Double> masses = new HashMap<Integer, Double>();
  
  MassBlock(List<Reader.LineEntry> lines)
  {
    // remove first line
    lines.remove(0);
    
    for (Reader.LineEntry line : lines)
    {
      if (line.isEmpty)
        continue;
      
      int pdgId = Integer.parseInt(line.parts.get(0));
      double mass = Double.parseDouble(line.parts.get(1));
      
      this.masses.put(pdgId, mass);
      
    }
  }
  //----------------------------------------------------------------------
  
  /** @return null if the particle is not found (i.e. the information
      was not contained in the SLHA file). Also checks the anti-particle.
    */
  public Double getMass(int pdgId)
  {
    if (masses.containsKey(pdgId))
      return masses.get(pdgId);

    // check also anti-particles
    return masses.get(- pdgId);
  }
  //----------------------------------------------------------------------

  public Set<Integer> getParticleIds()
  {
    return this.masses.keySet();
  }
  
  //----------------------------------------------------------------------

}
