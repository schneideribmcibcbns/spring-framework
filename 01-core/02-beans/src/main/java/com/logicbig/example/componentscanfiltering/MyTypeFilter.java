package com.logicbig.example.componentscanfiltering;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Arrays;


public class MyTypeFilter implements TypeFilter {
    private static final String RunnableName = Runnable.class.getName();

    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String[] interfaceNames = classMetadata.getInterfaceNames();
        if (Arrays.stream(interfaceNames).anyMatch(RunnableName::equals)) {
            return true;
        }
        return false;
    }
}