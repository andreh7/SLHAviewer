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
 * A class keeping the information of a XSECTION SLHA block.
 * 
 * See http://phystev.cnrs.fr/wiki/2013:groups:tools:slha for the 
 * format of the block.
 * 
 * @author holzner
 */
public class XsectBlock
{
  private final double sqrts;
  private final Particle[] beamParticles = new Particle[2];

  private final List<Particle> finalStateParticles = new ArrayList<Particle>();
  
  private final List<Entry> entries = new ArrayList<Entry>();
  
  //----------------------------------------------------------------------

  public static class Entry
  {
    // TODO: we could use an enum here (or named constants)
    public final int scaleScheme;
    public final int qcdOrder;
    public final int ewOrder;
    public final double kappaFactorization;
    public final double kappaRenormalization;
    
    /** this is the LHAPDF code of the PDF used to calculate the cross section */
    public final int pdfId;
    
    /** cross section in pb */
    public final double xsect;
    
    /** name of the code used to calculate the cross section.
        Currently we assume that there can be no spaces in this name 
      */
    public final String programName;
 
    /** version of the code used to calculate the cross section.
        Currently we assume that there can be no spaces in this name
     */
    public final String programVersion;

    Entry(int scaleScheme, int qcdOrder, int ewOrder, double kappaFactorization, double kappaRenormalization, int pdfId, double xsect, String programName, String programVersion)
    {
      this.scaleScheme = scaleScheme;
      this.qcdOrder = qcdOrder;
      this.ewOrder = ewOrder;
      this.kappaFactorization = kappaFactorization;
      this.kappaRenormalization = kappaRenormalization;
      this.pdfId = pdfId;
      this.xsect = xsect;
      this.programName = programName;
      this.programVersion = programVersion;
    }
    
  }
  //----------------------------------------------------------------------
  
  public XsectBlock(List<Reader.LineEntry> lines)
  {
    Reader.LineEntry firstLine = lines.remove(0);
    
    this.sqrts = firstLine.doublePart(1);
    this.beamParticles[0] = Particles.get(firstLine.intPart(2));
    this.beamParticles[1] = Particles.get(firstLine.intPart(3));
    
    int numFinalStateParticles = firstLine.intPart(4);
    
    for (int i = 0; i < numFinalStateParticles; ++i)
      this.finalStateParticles.add(Particles.get(firstLine.intPart(5 + i)));
    
    // parse remaining lines
    for (Reader.LineEntry line : lines)
    {
      
      if (line.isEmpty)
        continue;
      
      Entry entry = new Entry(
        line.intPart(0), // scaleScheme
        line.intPart(1), // qcdOrder
        line.intPart(2), // ewOrder
        line.doublePart(3), // kappaFactorization
        line.doublePart(4), // kappaRenormalization
        line.intPart(5), // pdfId
        line.doublePart(6), // xsect
        line.parts.get(7), // programName
        line.parts.get(8) // programVersion
      );
      
      entries.add(entry);  
        
    } // loop over lines
  }
  
  //----------------------------------------------------------------------

  public List<Entry> getEntries()
  {
    return Collections.unmodifiableList(entries);
  }
  
  //----------------------------------------------------------------------

  public Particle getBeamParticle(int index)
  {
    return this.beamParticles[index];
  }
  
  //----------------------------------------------------------------------

  public List<Particle> getFinalStateParticles()
  {
    return Collections.unmodifiableList(finalStateParticles);
  }

  //----------------------------------------------------------------------

  public double getSqrts()
  {
    return sqrts;
  }

  //----------------------------------------------------------------------

}
