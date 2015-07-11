package helpers;

import javax.servlet.annotation.WebServlet;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.vaadin.data.Container;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;

import de.syngenio.vaadin.synergy.builder.SynergyBuilder;

public class WorldHelper
{

    public static HierarchicalContainer getNavigationHierarchy() {
        return new SynergyBuilder() {{
            addItem(item().withCaption("First"));
            addItem(
                    item().asGroup().withCaption("Resources").withChildren( 
                            item().asGroup().withCaption("Assets").withChildren(
                                    item().withCaption("Machines").withTargetNavigationState("view/Machines"),
                                    item().withCaption("Real Estate").withTargetNavigationState("view/Real Estate"),
                                    item().withCaption("Patents").withTargetNavigationState("view/Patents")),
                            item().withCaption("People").withTargetNavigationState("view/People")));
            addItem(item().withCaption("Something"));
            addItem(item().asGroup().withCaption("Processes").withChildren(
                    item().withCaption("Core").withTargetNavigationState("view/Core Processes"),
                    item().withCaption("Auxiliary").withTargetNavigationState("view/Auxiliary Processes")));
            addItem(item().withCaption("More"));
        }}.build();
    }

    public static HierarchicalContainer getImageNavigation()
    {
        return new SynergyBuilder() {{
            addItem(item().withCaption("Bookmark").stacked().withIcon(FontAwesome.BOOKMARK).withGlyphSize("4em").withIconSelected(FontAwesome.BOOKMARK_O).withTargetNavigationState("view/Bookmark"));
            addItem(item().withCaption("Bullhorn").withIcon(new ThemeResource("img/bullhorn.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/bullhorn_selected.png")).withTargetNavigationState("view/Bullhorn"));
            addItem(item().withCaption("Bullseye").withIcon(new ThemeResource("img/bullseye.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/bullseye_selected.png")).withTargetNavigationState("view/Bullseye"));
            addItem(item().withCaption("Credit Card").withIcon(new ThemeResource("img/cc.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/cc_selected.png")).withTargetNavigationState("view/Credit Card"));
            addItem(item().withCaption("Desktop").withIcon(new ThemeResource("img/desktop.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/desktop_selected.png")).withTargetNavigationState("view/Desktop"));
            addItem(item().withCaption("Download").withIcon(new ThemeResource("img/download.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/download_selected.png")).withTargetNavigationState("view/Download"));
            addItem(item().withCaption("Money").withIcon(new ThemeResource("img/money.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/money_selected.png")).withTargetNavigationState("view/Money"));
            addItem(item().withCaption("Mortar-Board").withIcon(new ThemeResource("img/mortar-board.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/mortar-board_selected.png")).withTargetNavigationState("view/Mortar-Board"));
            addItem(item().withCaption("Paper-Plane").withIcon(new ThemeResource("img/paper-plane.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/paper-plane_selected.png")).withTargetNavigationState("view/Paper-Plane"));
            addItem(item().withCaption("Paw").withIcon(new ThemeResource("img/paw.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/paw_selected.png")).withTargetNavigationState("view/Paw"));
            addItem(item().withCaption("Rocket").withIcon(new ThemeResource("img/rocket.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/rocket_selected.png")).withTargetNavigationState("view/Rocket"));
            addItem(item().withCaption("Shekel").withIcon(new ThemeResource("img/shekel.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/shekel_selected.png")).withTargetNavigationState("view/Shekel"));
            addItem(item().withCaption("Tachometer").withIcon(new ThemeResource("img/tachometer.png")).withImageWidth("64px").withImageHeight("64px").withIconSelected(new ThemeResource("img/tachometer_selected.png")).withTargetNavigationState("view/Tachometer"));
        }}.build();
    }

    public static Container getHubNavigation()
    {
        return new SynergyBuilder() {{
            // find World UIs
            Reflections reflections = new Reflections(worlds.PackageTag.class.getPackage().getName(), new SubTypesScanner(), new TypeAnnotationsScanner());
            for (Class webServletClass : reflections.getTypesAnnotatedWith(WebServlet.class)) {
                WebServlet ws = (WebServlet) webServletClass.getAnnotation(WebServlet.class);
                String path = ws.value()[0].replaceAll("/\\*$", "");
                addItem(item(path).withCaption(webServletClass.getEnclosingClass().getSimpleName()).withTargetNavigationState(path));
            }
        }}.build();
    }

}