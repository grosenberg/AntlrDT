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

import net.certiv.antlrdt.graph.behaviors.EdgeLayoutBehavior;
import net.certiv.antlrdt.graph.behaviors.GraphLayoutBehavior;
import net.certiv.antlrdt.graph.behaviors.NodeLayoutBehavior;
import net.certiv.antlrdt.graph.handlers.DoubleClickHandler;
import net.certiv.antlrdt.graph.handlers.PanOnDragHandler;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.parts.FXCanvasFactory;
import net.certiv.antlrdt.graph.parts.TreeEdgePart;
import net.certiv.antlrdt.graph.parts.TreeGraphPart;
import net.certiv.antlrdt.graph.parts.TreeNodePart;
import net.certiv.antlrdt.graph.parts.TreeNodePartAnchorProvider;
import net.certiv.antlrdt.graph.parts.TreePartsFactory;
import net.certiv.antlrdt.graph.parts.TreeRootPart;

public class TreeViewModule extends MvcFxModule {

	private static final String FB_HOVER = DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER;
	private static final String FB_SELECTION = DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER;

	@Override
	protected void configure() {
		super.configure();

		bind(IFXCanvasFactory.class).to(FXCanvasFactory.class);

		bindGraphPartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeGraphPart.class));
		bindNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeNodePart.class));
		bindEdgePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), TreeEdgePart.class));
	}

	protected void bindGraphPartAdapters(MapBinder<AdapterKey<?>, Object> binder) {
		binder.addBinding(AdapterKey.defaultRole()).to(LayoutContext.class);
		binder.addBinding(AdapterKey.defaultRole()).to(GraphLayoutBehavior.class);
	}

	protected void bindNodePartAdapters(MapBinder<AdapterKey<?>, Object> binder) {
		binder.addBinding(AdapterKey.defaultRole()).to(NodeLayoutBehavior.class);

		binder.addBinding(AdapterKey.defaultRole()).to(TreeNodePartAnchorProvider.class);
		binder.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);

		binder.addBinding(AdapterKey.role(FB_HOVER)).to(ShapeOutlineProvider.class);
		binder.addBinding(AdapterKey.role(FB_SELECTION)).to(ShapeBoundsProvider.class);

		// mouse drag support
		binder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
		binder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);

		// context menu policy
		// binder.addBinding(AdapterKey.defaultRole()).to(NodeContextMenuHandler.class);

		// double click policy
		binder.addBinding(AdapterKey.defaultRole()).to(DoubleClickHandler.class);
	}

	protected void bindEdgePartAdapters(MapBinder<AdapterKey<?>, Object> binder) {
		binder.addBinding(AdapterKey.defaultRole()).to(EdgeLayoutBehavior.class);

		// hover feedback provider
		// binder.addBinding(AdapterKey.role(FB_HOVER)).to(GeometricOutlineProvider.class);
	}

	@Override
	protected void bindRootPartAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> binder) {
		binder.addBinding(AdapterKey.defaultRole()).to(TreeRootPart.class);
	}

	@Override
	protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> binder) {
		super.bindIRootPartAdaptersForContentViewer(binder);

		binder.addBinding(AdapterKey.defaultRole()).to(PanOnDragHandler.class);
	}

	@Override
	protected void bindIViewerAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> binder) {
		super.bindIViewerAdaptersForContentViewer(binder);

		binder.addBinding(AdapterKey.defaultRole()).to(DiagramModel.class);
	}

	@Override
	protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> binder) {
		binder.addBinding(AdapterKey.defaultRole()).to(TreePartsFactory.class);
	}

	@Override
	protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> binder) {
		super.bindAbstractContentPartAdapters(binder);

		binder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);
		binder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);
	}
}
