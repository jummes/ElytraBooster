package com.github.jummes.elytrabooster.portal.shape;

import com.github.jummes.elytrabooster.portal.Portal;
import com.github.jummes.libs.annotation.Enumerable;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.model.Model;
import com.github.jummes.libs.model.ModelPath;
import com.github.jummes.libs.model.wrapper.LocationWrapper;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Enumerable.Child
@Enumerable.Displayable(name = "&c&lComposed Shape", description = "gui.portal.shape.composed.description", headTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdhMGZjNmRjZjczOWMxMWZlY2U0M2NkZDE4NGRlYTc5MWNmNzU3YmY3YmQ5MTUzNmZkYmM5NmZhNDdhY2ZiIn19fQ==")
public class ComposedShape extends Shape {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD, description = "gui.portal.shape.composed.shapes")
    private List<SingleShape> shapes;
    @Serializable(headTexture = HEAD, description = "gui.portal.shape.composed.center")
    private LocationWrapper center;

    public ComposedShape(ModelPath<Portal> path) {
        this(path.getRoot().getShape().getAxis(), Lists.newArrayList(),
                new LocationWrapper(path.getRoot().getShape().getCenterPoint()));
    }

    public ComposedShape(char axis, List<SingleShape> shapes, LocationWrapper center) {
        super(axis);
        this.shapes = shapes;
        this.center = center;
    }

    @SuppressWarnings("unchecked")
    public static ComposedShape deserialize(Map<String, Object> map) {
        char axis = ((String) map.get("axis")).charAt(0);
        List<SingleShape> shapes = (List<SingleShape>) map.get("shapes");
        LocationWrapper center = (LocationWrapper) map.get("center");
        return new ComposedShape(axis, shapes, center);
    }

    @Override
    public boolean isInPortalArea(Location location, double epsilon) {
        return shapes.stream().sequential().reduce(false, (bool, shape) -> {
            switch (shape.mode) {
                case UNION:
                    return bool || shape.getShape().isInPortalArea(location, epsilon);
                case INTERSECTION:
                    return bool && shape.getShape().isInPortalArea(location, epsilon);
                case DIFFERENCE:
                    return bool && !shape.getShape().isInPortalArea(location, epsilon);
            }
            return false;
        }, (b1, b2) -> b1 && b2);
    }

    @Override
    protected List<Location> buildPoints(boolean isBlockOutline) {
        double epsilon = .05;
        List<Location> locations = Lists.newArrayList();
        shapes.forEach(shape -> locations.addAll(shape.getShape().getPoints()));
        return locations.stream().filter(location -> !isInPortalArea(location, epsilon)).collect(Collectors.toList());
    }

    @Override
    public Location getCenterPoint() {
        return center.getWrapped();
    }

    @Getter
    public static class SingleShape implements Model {

        private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

        @Serializable(headTexture = HEAD, stringValue = true, description = "gui.portal.shape.composed.mode")
        private CompositionMode mode;
        @Serializable(headTexture = HEAD, description = "gui.portal.shape.composed.shape")
        private Shape shape;

        public SingleShape(ModelPath<Portal> path) {
            this(CompositionMode.UNION, new CircleShape(path));
        }

        public SingleShape(CompositionMode mode, Shape shape) {
            this.mode = mode;
            this.shape = shape;
        }

        public static SingleShape deserialize(Map<String, Object> map) {
            Shape shape = (Shape) map.get("shape");
            CompositionMode mode = CompositionMode.valueOf((String) map.get("mode"));
            return new SingleShape(mode, shape);
        }

        @Override
        public ItemStack getGUIItem() {
            return new ItemStack(Material.CARROT);
        }

        public enum CompositionMode {
            UNION, INTERSECTION, DIFFERENCE;
        }

    }

}
