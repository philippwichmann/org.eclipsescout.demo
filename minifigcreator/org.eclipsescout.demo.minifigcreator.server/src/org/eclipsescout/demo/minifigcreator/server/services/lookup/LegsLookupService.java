package org.eclipsescout.demo.minifigcreator.server.services.lookup;

import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minifigcreator.server.data.IMinifigDataStoreService;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.ILegsLookupService;

public class LegsLookupService extends AbstractPartLookupService implements ILegsLookupService {

  @Override
  protected List<Part> createPartsList(LookupCall call) {
    return SERVICES.getService(IMinifigDataStoreService.class).getAvailableLegs();
  }
}
