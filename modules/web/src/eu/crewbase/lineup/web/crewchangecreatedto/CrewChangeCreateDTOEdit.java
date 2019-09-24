package eu.crewbase.lineup.web.crewchangecreatedto;

import com.haulmont.cuba.gui.screen.*;
import eu.crewbase.lineup.entity.dto.CrewChangeCreateDTO;

@UiController("lineup$CrewChangeCreateDTO.edit")
@UiDescriptor("crew-change-create-dto-edit.xml")
@EditedEntityContainer("crewChangeCreateDTODc")
@LoadDataBeforeShow
public class CrewChangeCreateDTOEdit extends StandardEditor<CrewChangeCreateDTO> {
}