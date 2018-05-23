package io.jbehavetutorials;

import io.jbehavetutorials.steps.facebook.FacebookStatusPost;
import io.jbehavetutorials.steps.wallethub.WalletHubReviewInsurance;
import io.jbehavetutorials.steps.weatherapi.WeatherAPITest;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.junit.JUnitStories;

import java.text.SimpleDateFormat;
import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.SurefireReporter;
import org.jbehave.core.steps.*;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

/**
 * Created by prasantabiswas on 21/05/18.
 */
public class StoryMap extends JUnitStories {
    
    public StoryMap() {
        configuredEmbedder().embedderControls().doGenerateViewAfterStories(true).doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(true).useThreads(1);
    }

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        // Start from default ParameterConverters instance
        ParameterConverters parameterConverters = new ParameterConverters();
        // Start from default ParameterControls instance
        ParameterControls parameterControls = new ParameterControls();
        // factory to allow parameter conversion and loading from external resources (used by StoryParser too)
        ExamplesTableFactory examplesTableFactory = new ExamplesTableFactory(new LocalizedKeywords(), new LoadFromClasspath(embeddableClass),parameterConverters,parameterControls, new TableTransformers());
        // add custom converters
        parameterConverters.addConverters(new DateConverter(new SimpleDateFormat("yyyy-MM-dd")),
                new ExamplesTableConverter(examplesTableFactory));
        return new MostUsefulConfiguration()
                //.usePendingStepStrategy(new FailingUponPendingStep())
                .useStoryControls(new StoryControls().doDryRun(false))
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                .withSurefireReporter(new SurefireReporter(embeddableClass))
                    .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                    .withDefaultFormats()
                    .withFormats(CONSOLE, TXT, HTML, XML)
                    .withFailureTrace(true)
                    .withFailureTraceCompression(true))
            .useParameterConverters(parameterConverters)
            .useParameterControls(parameterControls);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new FacebookStatusPost(), new WalletHubReviewInsurance(), new WeatherAPITest());
//        return new ScanningStepsFactory(configuration(),"io.jbehavetutorials.steps"); //requires reflection dependency
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/*.story", "**/excluded*.story");
                
    }
        
}