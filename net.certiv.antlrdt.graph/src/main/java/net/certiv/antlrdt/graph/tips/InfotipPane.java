package net.certiv.antlrdt.graph.tips;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.eclipse.swt.graphics.RGB;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.util.FXUtil;
import net.certiv.dsl.core.util.Strings;

public class InfotipPane extends TitledPane {

	private static final double H_PADDING = 4d;
	private static final double V_PADDING = 2d;
	private static final double H_SPACING = 8d;

	private static final String[] TIP_LABELS = { "Description:", "Location:" };

	private NodeModel model;

	private BorderPane content;
	private VBox header;
	private VBox labels;
	private VBox values;

	private Color bgfill = Color.LIGHTGOLDENRODYELLOW;

	public InfotipPane(Infotip tip) {
		super();
		this.model = tip.getModel();

		bgfill = getTooltipFill(model);

		setCollapsible(false);
		setText("Details");
		setContent(createContent());

		getStyleClass().add(Infotip.TIP_CLASS);

		// Color fill = Color.YELLOW; // getTooltipFill(model);
		// setTextFill(FXUtil.contrastColor(fill));
		// setBackground(new Background(new BackgroundFill(fill, corners, insets)));
	}

	private Node createContent() {
		header = headerBlock();
		labels = labelsBlock();
		values = valuesBlock();

		content = new BorderPane();
		content.setTop(header);
		content.setLeft(labels);
		content.setCenter(values);
		content.setBackground(new Background(new BackgroundFill(bgfill, CornerRadii.EMPTY, Insets.EMPTY)));

		return content;
	}

	private VBox headerBlock() {
		Label text = new Label();
		text.setText(Strings.encode(model.getText()));
		text.setGraphic(new ImageView(model.getIconUrl()));
		text.setGraphicTextGap(H_PADDING);
		text.getStyleClass().add(Infotip.TIP_TITLE);
		text.setSnapToPixel(true);
		text.setTextFill(FXUtil.contrastColor(bgfill));

		Separator sep = new Separator();
		sep.setValignment(VPos.CENTER);

		VBox box = new VBox(V_PADDING);
		VBox.setMargin(box, new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING));
		box.setAlignment(Pos.CENTER_LEFT);
		box.getChildren().addAll(text, sep);
		return box;
	}

	private VBox labelsBlock() {
		VBox box = new VBox(V_PADDING);
		VBox.setMargin(box, new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING + H_SPACING));
		box.setAlignment(Pos.CENTER_LEFT);

		for (String text : TIP_LABELS) {
			Label child = new Label(text);
			child.getStyleClass().addAll(Infotip.TIP_KEY, model.getCssClass());
			child.setSnapToPixel(true);
			child.setTextFill(FXUtil.contrastColor(bgfill));
			box.getChildren().add(child);
		}
		return box;
	}

	private VBox valuesBlock() {
		VBox box = new VBox(V_PADDING);
		VBox.setMargin(box, new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING + H_SPACING));
		box.setAlignment(Pos.CENTER_LEFT);

		String desc = String.format("%s  <%s>", model.getNodeDescription(), model.getNodeType());
		String loc = String.format("line %s : col %s (length %s)", model.getNodeSourceLine(),
				model.getNodeSourceOffsetInLine(), model.getNodeSourceLength());

		for (String text : new String[] { desc, loc }) {
			Label child = new Label(text);
			child.getStyleClass().addAll(Infotip.TIP_VALUE, model.getCssClass());
			child.setSnapToPixel(true);
			child.setTextFill(FXUtil.contrastColor(bgfill));
			box.getChildren().add(child);
		}
		return box;
	}

	protected Color getTooltipFill(NodeModel model) {
		RGB rgb = AntlrCore.getDefault().getPrefsManager().getRGB(PrefsKey.PT_TOOLTIP_COLOR);
		return Color.rgb(rgb.red, rgb.green, rgb.blue);
	}
}
