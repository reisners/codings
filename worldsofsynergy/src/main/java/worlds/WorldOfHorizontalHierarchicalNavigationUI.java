package worlds;

import helpers.WorldHelper;

import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.syngenio.vaadin.synergy.SynergyView;
import de.syngenio.vaadin.synergy.layout.HorizontalSynergyLayoutFactory;
import de.syngenio.vaadin.synergy.layout.VerticalSynergyLayoutFactory;

@Theme("default")
public class WorldOfHorizontalHierarchicalNavigationUI extends UI
{
    @WebServlet(value = "/horizontal/hierarchical/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = WorldOfHorizontalHierarchicalNavigationUI.class)
    public static class Servlet extends VaadinServlet {
    }

    private static final Logger log = LoggerFactory.getLogger(WorldOfHorizontalHierarchicalNavigationUI.class);
    
    @Override
    protected void init(VaadinRequest request)
    {
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setSizeFull();
        SynergyView synergyViewH1 = new SynergyView(new HorizontalSynergyLayoutFactory(), WorldHelper.getNavigationHierarchy());
        synergyViewH1.addStyleName("h1");
        synergyViewH1.setHeightUndefined();
        synergyViewH1.setWidth("100%");
//        synergyViewH1.setWidthUndefined();
        vlayout.addComponent(synergyViewH1);
        vlayout.setExpandRatio(synergyViewH1, 0f);
        
        SynergyView synergyViewH2 = new SynergyView(new HorizontalSynergyLayoutFactory(), synergyViewH1);
        synergyViewH1.addStyleName("h2");
        synergyViewH2.setHeightUndefined();
        synergyViewH2.setWidth("100%");
//        synergyViewH2.setWidthUndefined();
        vlayout.addComponent(synergyViewH2);
        vlayout.setExpandRatio(synergyViewH2, 0f);
        
        synergyViewH1.setSubView(synergyViewH2);
        
        HorizontalLayout hlayout = new HorizontalLayout();
        hlayout.setSizeFull();
        SynergyView synergyViewVertical = new SynergyView(new VerticalSynergyLayoutFactory(Alignment.MIDDLE_LEFT), synergyViewH2);
        synergyViewVertical.setWidthUndefined();
        synergyViewVertical.setHeight("100%");
//        synergyView.setWidth("30%");
        hlayout.addComponent(synergyViewVertical);
        hlayout.setExpandRatio(synergyViewVertical, 0f);

        vlayout.addComponent(hlayout);
        vlayout.setExpandRatio(hlayout, 1f);
        
        synergyViewH2.setSubView(synergyViewVertical);
        
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setId("content");
        hlayout.addComponent(panel);
        hlayout.setExpandRatio(panel, 1f);

        setContent(vlayout);
        
        setNavigator(new Navigator(this, panel));
        final NavigationView genericView = new NavigationView();
        getNavigator().addView("", genericView);
        getNavigator().addView("view", genericView);
    }
    
    private static class NavigationView extends Label implements View {
        @Override
        public void enter(ViewChangeEvent event)
        {
            setValue(event.getParameters());
        }
    }
}
