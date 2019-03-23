package gwtAppTest.client.upload;

import com.google.gwt.user.client.ui.DialogBox;
import gwtAppTest.shared.TmpFile;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ShowPic extends DialogBox {
	FlexTable resultTable;
	public ShowPic(TmpFile tmp){
		
		DockPanel dockPanel = new DockPanel();
		setWidget(dockPanel);
		dockPanel.setSize("271px", "212px");
		
		Button btnClose = new Button("close");
		btnClose.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		dockPanel.add(btnClose, DockPanel.SOUTH);
		dockPanel.setCellHorizontalAlignment(btnClose, HasHorizontalAlignment.ALIGN_CENTER);
		
		resultTable = new FlexTable();
		dockPanel.add(resultTable, DockPanel.CENTER);
		resultTable.setSize("271px", "192px");
		
        Image image = new Image();
        image.setUrl(tmp.getImageUrl());
		
	    resultTable.setWidget(0, 0, image);
        resultTable.setText(1, 0,"title:"+ tmp.getTitle());
        resultTable.setText(2, 0, "description"+tmp.getDescription());
	
	}
}
