package net.certiv.antlrdt.graph.view.tree;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.fx.swt.canvas.IFXCanvasFactory;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.HoverOnHoverHandler;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.policies.TransformPolicy;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import com.google.inject.multibindings.MapBinder;

import net.certiv.antlrdt.graph.handlers.PanOnDragHandler;
import net.certiv.antlrdt.graph.parts.FXCanvasFactory;
import net.certiv.antlrdt.graph.parts.TreeEdgePart;
import net.certiv.antlrdt.graph.parts.TreeModelPart;
import net.certiv.antlrdt.graph.parts.TreeNodePart;
import net.certiv.antlrdt.graph.parts.TreeNodePartAnchorProvider;
import net.certiv.antlrdt.graph.parts.TreePartsFactory;
import net.certiv.antlrdt.graph.parts.TreeRootPart;

public class TreeViewModule extends MvcFxModule {

	@Override
	protected void configure() {
		super.configure();

		bind(IFXCanvasFactory.class).to(FXCanvasFactory.class);
		bindGraphPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeModelPart.class));
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeNodePart.class));
		bindEdgePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeEdgePart.class));
	}

	protected void bindGraphPartAdapters(MapBinder<AdapterKey<?>, Object> mapper) {
		mapper.addBinding(AdapterKey.defaultRole()).to(LayoutContext.class);
		// mapper.addBinding(AdapterKey.defaultRole()).to(GraphLayoutBehavior.class);
	}

	protected void bindNodePartAdapters(MapBinder<AdapterKey<?>, Object> mapper) {
		mapper.addBinding(AdapterKey.defaultRole()).to(TreeNodePartAnchorProvider.class);
		mapper.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);

		mapper.addBinding(AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeOutlineProvider.class);
		mapper.addBinding(AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER))
				.to(ShapeBoundsProvider.class);

		// support moving nodes via mouse drag
		mapper.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
		mapper.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// click policies: context menu & double click
		// mapper.addBinding(AdapterKey.defaultRole()).to(ShowNodeContextMenuOnClickHandler.class);
		// mapper.addBinding(AdapterKey.defaultRole()).to(DoubleClickHandler.class);
	}

	protected void bindEdgePartAdapters(MapBinder<AdapterKey<?>, Object> mapper) {
		mapper.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);
	}

	@Override
	protected void bindRootPartAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> mapper) {
		mapper.addBinding(AdapterKey.defaultRole()).to(TreeRootPart.class);
	}

	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> mapper) {
		mapper.addBinding(AdapterKey.defaultRole()).to(TreePartsFactory.class);
	}

	@Override
	protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> mapper) {
		super.bindIRootPartAdaptersForContentViewer(mapper);

		mapper.addBinding(AdapterKey.defaultRole()).to(PanOnDragHandler.class);
	}

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> mapper) {
		super.bindAbstractContentPartAdapters(mapper);

		mapper.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);
		mapper.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
	}
}
