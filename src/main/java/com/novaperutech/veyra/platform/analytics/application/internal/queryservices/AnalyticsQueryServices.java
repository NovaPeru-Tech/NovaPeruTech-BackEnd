// AnalyticsQueryServices.java
package com.novaperutech.veyra.platform.analytics.application.internal.queryservices;

import com.novaperutech.veyra.platform.analytics.domain.model.queries.*;
import com.novaperutech.veyra.platform.analytics.domain.model.valueobjects.*;

public interface AnalyticsQueryServices {
    GeneralStats   handle(GetGeneralAnalyticsQuery query);
    InventoryStats handle(GetInventoryAnalyticsQuery query);
    ResidentStats  handle(GetResidentStatisticsQuery query);
}
