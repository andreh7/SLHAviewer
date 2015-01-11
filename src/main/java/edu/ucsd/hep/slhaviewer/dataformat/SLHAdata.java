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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the information obtained from an SLHA file
 * @author holzner
 */
public class SLHAdata
{
  private final List<Reader.LineEntry> origLines;
  
  // maps from mother pdgId to decay block
  Map<Integer, DecayBlock> decays = new HashMap<Integer, DecayBlock>();

  private MassBlock massBlock;

  private List<XsectBlock> crossSections = new ArrayList<XsectBlock>();
  
  //----------------------------------------------------------------------

  SLHAdata(List<Reader.LineEntry> origLines)
  {
    this.origLines = origLines;
  }
  
  //----------------------------------------------------------------------

  void addDecay(DecayBlock decayBlock)
  {
    this.decays.put(decayBlock.getMother().pdgId, decayBlock);
  }
  
  //----------------------------------------------------------------------

  public Double getParticleMass(int pdgId)
  {
    throw new UnsupportedOperationException("not yet implemented");
  }
  
  //----------------------------------------------------------------------

  public DecayBlock getDecayBlock(int pdgId)
  {
    return this.decays.get(pdgId);
  }
  
  //----------------------------------------------------------------------

  public Double getParticleDecayWidth(int pdgId)
  {
    DecayBlock db = getDecayBlock(pdgId);
    if (db == null)
      return null;
    
    return db.getDecayWidth();
    
  }

  //----------------------------------------------------------------------

  void setMasses(MassBlock massBlock)
  {
    this.massBlock = massBlock;
  }

  //----------------------------------------------------------------------

  public MassBlock getMassBlock()
  {
    return this.massBlock;
  }

  //----------------------------------------------------------------------

  void addCrossSection(XsectBlock xsectBlock)
  {
    this.crossSections.add(xsectBlock);
  }

  //----------------------------------------------------------------------

}
