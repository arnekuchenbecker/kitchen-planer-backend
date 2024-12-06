/*
 * KitchenPlanerApp is the android app frontend for the KitchenPlaner, a tool
 * to cooperatively plan a meal plan for a campout.
 * Copyright (C) 2023-2024 Arne Kuchenbecker, Antonia Heiming, Anton Kadelbach, Sandra Lanz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.scouts.kitchenplanerbackend.projectdtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of a project used by the controllers and the services
 *
 * @param versionNumber      The version number of the project (except for the image)
 * @param imageVersionNumber The version of the picture of the project
 * @param name               Name of the project
 * @param id                 Online Id of the project (or 0 if the project doesn't have a online Id yet)
 * @param meals              All meals within the project
 * @param startDate          Start date of the project
 * @param endDate            Date when the project ends
 * @param allergenPeople     All allergen people belonging to the project
 * @param recipes            All recipes used in the project including their meal slots
 * @param unitConversions    All unit conversions relevant for the project
 * @param personNumberChange All changes of "eating" persons during the project
 */
public record Project(long versionNumber, long imageVersionNumber, String name, long id, List<String> meals,
                      Date startDate, Date endDate,
                      List<AllergenPerson> allergenPeople, List<RecipeForProject> recipes,
                      List<UnitConversion> unitConversions, List<PersonNumberChange> personNumberChange
) {}
