package auxiliary;

import java.util.ArrayList;

/**
 * Created by Mateusz on 10.05.2016.
 * Project WhatToCook
 */
public class LanguagePackageList extends ArrayList<LanguagePackage>{
    public LanguagePackage get(String language)
    {
        for(int i = 0; i < this.size();i++)
        {
            if(this.get(i).getName().equals(language))
                return this.get(i);
        }
        return null;
    }
}
