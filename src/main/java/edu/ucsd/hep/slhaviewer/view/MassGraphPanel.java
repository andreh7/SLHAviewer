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
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A graphical view of the mass spectrum
 * @author holzner
 */
public class MassGraphPanel extends JPanel
{
  private final XYSeriesCollection dataset;
  private final XYLineAndShapeRenderer renderer;
  
  //----------------------------------------------------------------------
  
  public MassGraphPanel()
  {
    this.setLayout(new BorderLayout());
    
    dataset = new XYSeriesCollection();

    JFreeChart chart = ChartFactory.createXYLineChart(
            "Mass Spectrum",
            "",
            "mass [GeV]",
            dataset,
            PlotOrientation.VERTICAL,
            false, // no legend
            true, // add tooltips
            false // add urls
    );

    // get a reference to the plot for further customisation...
    XYPlot plot = (XYPlot) chart.getPlot();

    renderer = (XYLineAndShapeRenderer) plot.getRenderer();
    
    // change the auto tick unit selection to integer units only...
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
    domainAxis.setTickMarksVisible(false);
    domainAxis.setTickLabelsVisible(false);
    
    chart.setBackgroundPaint(Color.WHITE); // 'outside'
    plot.setBackgroundPaint(Color.WHITE); // 'inside'
    plot.setRangeGridlinePaint(Color.DARK_GRAY);
    
    this.add(new ChartPanel(chart), BorderLayout.CENTER);

  }
  
  //----------------------------------------------------------------------
  
  public static MassGraphPanel make(SLHAdata data)
  {
    MassGraphPanel retval = new MassGraphPanel();
    retval.setParticles(data);
    return retval;
  }
  
  //----------------------------------------------------------------------
  
  private void addParticle(MassBlock massBlock, int pdgId, double column, Color color)
  {
    final double halfBarWidth = 0.3;
    Double mass = massBlock.getMass(pdgId);
    
    if (mass == null)
      // don't draw this if the particle is not present
      return;
    
    // some masses are negative in the SLHA file
    mass = Math.abs(mass);
    
    XYSeries series = new XYSeries(Particles.get(pdgId).shortName);
    series.add(column - halfBarWidth, mass);
    series.add(column + halfBarWidth, mass);
    
    int seriesIndex = dataset.getSeriesCount();
    renderer.setSeriesPaint(seriesIndex, color);
    
    dataset.addSeries(series);

  }

  //----------------------------------------------------------------------
  
  public void setParticles(SLHAdata slhaData)
  {
    MassBlock massBlock = slhaData.getMassBlock();

    dataset.removeAllSeries();
    
    // five Higgs bosons
    addParticle(massBlock, 25, 0.9, Color.BLUE); // h
    addParticle(massBlock, 35, 0.9, Color.BLUE); // H
    addParticle(massBlock, 36, 0.9, Color.BLUE); // A
    addParticle(massBlock, 37, 1.1, Color.RED); // H+

    // sleptons and sneutrinos
    addParticle(massBlock, 2000011, 1.9, Color.BLUE);        // ~e_R
    addParticle(massBlock, 1000012, 1.9, Color.BLUE);        // ~nu_eL
    addParticle(massBlock, 1000013, 1.9, Color.BLUE);        // ~mu_L
    addParticle(massBlock, 2000013, 1.9, Color.BLUE);        // ~mu_R
    addParticle(massBlock, 1000014, 1.9, Color.BLUE);        // ~nu_muL
    addParticle(massBlock, 1000015, 2.1, Color.RED);        // ~tau_1
    addParticle(massBlock, 2000015, 2.1, Color.RED);        // ~tau_2
    addParticle(massBlock, 1000016, 2.1, Color.RED);        // ~nu_tauL

    // neutralinos and charginos
    addParticle(massBlock, 1000022, 2.9, Color.BLUE); // ~chi_10
    addParticle(massBlock, 1000023, 2.9, Color.BLUE); // ~chi_20
    addParticle(massBlock, 1000025, 2.9, Color.BLUE); // ~chi_30
    addParticle(massBlock, 1000035, 2.9, Color.BLUE); // ~chi_40
    addParticle(massBlock, 1000024, 3.1, Color.RED); // ~chi_1+
    addParticle(massBlock, 1000037, 3.1, Color.RED); // ~chi_2+

    // gluino and squarks
    addParticle(massBlock, 1000021, 3.8, Color.GREEN); // ~g

    addParticle(massBlock, 1000001, 3.9, Color.BLUE); // ~d_L
    addParticle(massBlock, 2000001, 3.9, Color.BLUE); // ~d_R
    addParticle(massBlock, 1000002, 3.9, Color.BLUE); // ~u_L
    addParticle(massBlock, 2000002, 3.9, Color.BLUE); // ~u_R
    addParticle(massBlock, 1000003, 3.9, Color.BLUE); // ~s_L
    addParticle(massBlock, 2000003, 3.9, Color.BLUE); // ~s_R
    addParticle(massBlock, 1000004, 3.9, Color.BLUE); // ~c_L
    addParticle(massBlock, 2000004, 3.9, Color.BLUE); // ~c_R

    addParticle(massBlock, 1000005, 4.1, Color.GREEN); // ~b_1
    addParticle(massBlock, 2000005, 4.1, Color.GREEN); // ~b_2
    addParticle(massBlock, 1000006, 4.1, Color.RED); // ~t_1
    addParticle(massBlock, 2000006, 4.1, Color.RED); // ~t_2   
  }
  
  //----------------------------------------------------------------------

}
