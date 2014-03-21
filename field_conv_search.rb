require 'dir_walker'

KFS_ROOT = "c:\\java\\projects\\kfs"

lookup_selector = lambda do |file_name|
	(file_name =~ /jspl$/i || file_name =~ /tag$/i)
end

lookup_reader = lambda do |fin, file_name|
	contents = fin.read
	matches = contents.scan(/(\<kul:lookup[^\/]+\/>)/m)
	if matches.size > 0
		clean_file = file_name.sub(/^c:\\java\\projects\\kfs\\work\\web-root\\WEB-INF\\tags/,"").gsub(/\\/,"\\\\\\\\")
		matches = matches.collect{|value| value[0]}.reject{|value| value.nil?}.collect{|value| value.gsub("\n"," ")}.collect{|value| value.gsub(/\s\s+/," ")}.select{|value| value =~ /fieldConversions/}
		matches.each do |value|
			bo_clazz_match = value.match(/boClassName=\"([^\"]+)\"/)
			#bo_clazz = bo_clazz_match.nil? ? "#{value} no business object class" : bo_clazz_match[1]
			bo_clazz = bo_clazz_match.nil? ? nil : bo_clazz_match[1]
			field_conversions_match = value.match(/fieldConversions=\"([^\"]+)\"/)
			#field_conversions = field_conversions_match.nil? ? "#{value} no field conversions" : field_conversions_match[1]
			field_conversions = field_conversions_match.nil? ? nil : field_conversions_match[1]
			#puts "\"#{bo_clazz}\",\"#{field_conversions}\",\"#{clean_file}\""
			
			if !bo_clazz.nil? && !field_conversions.nil?
				field_conversion_attributes = field_conversions.split(/,/).collect{|value| value.split(/:/)[0]}
				for fc in field_conversion_attributes
					puts "\t\tchecks.add(new FieldConversionCheck(#{bo_clazz}.class,\"#{fc}\",\"#{clean_file}\"));"
				end
			end
		end
	end
end
dir_walker(KFS_ROOT, lookup_selector, lookup_reader)