/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.mobile.client.application.breadcrumbs;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHyperlink;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * This is the top-level view of the application. Every time another presenter wants to reveal itself,
 * {@link BreadcrumbPresenterView} will add its content of the target inside the {@code mainContantPanel}.
 */
public class BreadcrumbsTabletView extends ViewImpl implements BreadcrumbsPresenter.MyView {
    public interface Binder extends UiBinder<Widget, BreadcrumbsTabletView> {
    }

    @UiField
    FlowPanel breadcrumbs;
    @UiField
    SimplePanel mainContentPanel;

    private final PlaceManager placeManager;

    @Inject
    public BreadcrumbsTabletView(final Binder binder, final PlaceManager placeManager) {
        this.placeManager = placeManager;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void clearBreadcrumbs(int breadcrumbSize) {
        breadcrumbs.clear();
        for (int i = 0; i < breadcrumbSize; ++i) {
            if (i > 0) {
                breadcrumbs.add(new InlineLabel(" > "));
            }
            breadcrumbs.add(new InlineHyperlink("Loading title...", placeManager.buildRelativeHistoryToken(i + 1)));
        }
    }

    @Override
    public void setBreadcrumbs(int index, String title) {
        InlineHyperlink hyperlink = (InlineHyperlink) breadcrumbs.getWidget(index * 2);
        if (title == null) {
            hyperlink.setHTML("Unknown title");
        } else {
            hyperlink.setHTML(title);
        }
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == BreadcrumbsPresenter.TYPE_SetMainContent) {
            mainContentPanel.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
