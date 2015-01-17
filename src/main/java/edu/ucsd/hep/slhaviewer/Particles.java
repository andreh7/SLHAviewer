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
package edu.ucsd.hep.slhaviewer;

import java.util.HashMap;
import java.util.Map;

/**
 * contains generic information about particles (names etc.)
 * 
 * Note that this does NOT contain masses and branching ratios
 * as we may want to compare two SLHA files with different masses etc.
 * 
 * @author holzner
 */
public class Particles
{
  private static Map<Integer, Particle> data = new HashMap<Integer, Particle>();
 
  static 
  {
    putParticle(24, "W+", "W boson");
    putParticle(25, "h", "light CP even Higgs boson");
    putParticle(35, "H", "heavy CP even Higgs boson");
    putParticle(36, "A", "CP odd Higgs boson");
    putParticle(37, "H+", "charged Higgs boson");
   
    putParticle(1, "d", "down quark");
    putParticle(- 1, "d*", "anti down quark");
    putParticle(2, "u", "up quark");
    putParticle(- 2, "u*", "anti up quark");
    putParticle(3, "s", "strange quark");
    putParticle(- 3, "s*", "anti strange quark");
    putParticle(4, "c", "charm quark");
    putParticle(- 4, "c*", "anti charm quark");
    putParticle(5, "b", "b quark");
    putParticle(- 5, "b*", "anti b quark");
    putParticle(6, "t", "top quark");
    putParticle(- 6, "t*", "anti top quark");

    putParticle(11, "e-", "electron");
    putParticle(- 11, "e+", "positron");
    putParticle(13, "mu-", "muon");
    putParticle(- 13, "mu+", "anti-muon");
    putParticle(15, "tau-", "tau");
    putParticle(- 15, "tau+", "anti-tau");
  
    putParticle(12, "nu_e", "electron neutrino");
    putParticle(- 12, "nu_e*", "electron anti-neutrino");
    putParticle(14, "nu_mu", "muon neutrino");
    putParticle(- 14, "nu_mu*", "muon anti-neutrino");
    putParticle(16, "nu_tau", "tau neutrino");
    putParticle(- 16, "nu_tau*", "tau anti-neutrino");
    
    putParticle(2212, "p", "proton");
    
    putParticle(1000001, "~d_L", "lefthanded down squark");
    putParticle(2000001, "~d_R", "righthanded down squark");
    putParticle(1000002, "~u_L", "lefthanded up squark");
    putParticle(2000002, "~u_R", "righthanded up squark");
    putParticle(1000003, "~s_L", "lefthanded strange squark");
    putParticle(2000003, "~s_R", "righthanded strange squark");
    putParticle(1000004, "~c_L", "lefthanded charm squark");
    putParticle(2000004, "~c_R", "righthanded charm squark");
    putParticle(1000005, "~b_1", "lighter sbottom");
    putParticle(2000005, "~b_2", "heavier sbottom");
    
    putParticle(1000006, "~t_1", "lighter stop");
    putParticle(2000006, "~t_2", "heavier stop");
    putParticle(- 1000006, "~t_1*", "lighter anti-stop");
    putParticle(- 2000006, "~t_2*", "heavier anti-stop");
    
    putParticle(1000011, "~e_L", "lefthanded selectron");
    putParticle(2000011, "~e_R", "righthanded selectron");
    putParticle(1000012, "~nu_eL", "electron sneutrino");
    putParticle(1000013, "~mu_L", "lefthanded smuon");
    putParticle(2000013, "~mu_R", "righthanded smuon");
    putParticle(1000014, "~nu_muL", "muon sneutrino");
    putParticle(1000015, "~tau_1", "lighter stau");
    putParticle(2000015, "~tau_2", "heavier stau");
    putParticle(1000016, "~nu_tauL", "tau sneutrino");

    putParticle(1000021, "~g", "gluino");

    putParticle(1000022, "~chi_10", "neutralino 1");
    putParticle(1000023, "~chi_20", "neutralino 2");
    putParticle(1000025, "~chi_30", "neutralino 3");
    putParticle(1000035, "~chi_40", "neutralino 4");
    putParticle(1000024, "~chi_1+", "chargino 1+");
    putParticle(1000037, "~chi_2+", "chargino 2+");

    putParticle(- 1000024, "~chi_1-", "chargino 1-");
    putParticle(- 1000037, "~chi_2-", "chargino 2-");

  }
  
  private static void putParticle(int pdgId, String shortName, String verboseName)
  {
    synchronized(data)
    {
        data.put(pdgId, new Particle(pdgId, shortName, verboseName));
    }
  }

  public static Particle get(int pdgId)
  {
    synchronized (data)
    {
      if (data.containsKey(pdgId))
        return data.get(pdgId);
    
      Particle particle = new Particle(pdgId, 
              "unknown particle " + pdgId,
              "unknown particle " + pdgId);
      data.put(pdgId, particle);
      
      return particle;
    }
  }
}
